package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.ai.AiAnalysisClient;
import fiap.com.br.petpulse.ai.AiAnalysisRequest;
import fiap.com.br.petpulse.ai.AiAnalysisResponse;
import fiap.com.br.petpulse.config.AiServiceProperties;
import fiap.com.br.petpulse.dto.request.SmartAlertRequest;
import fiap.com.br.petpulse.enums.AlertOrigin;
import fiap.com.br.petpulse.enums.AlertRiskLevel;
import fiap.com.br.petpulse.enums.AlertStatus;
import fiap.com.br.petpulse.model.AlertType;
import fiap.com.br.petpulse.model.IoTReading;
import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.repositories.AlertTypeRepository;
import fiap.com.br.petpulse.repositories.ClinicalHistoryRepository;
import fiap.com.br.petpulse.repositories.IoTReadingRepository;
import fiap.com.br.petpulse.repositories.SmartAlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IoTReadingAnalysisService {

    private static final String FALLBACK_AI_ALERT_TYPE = "ANOMALIA_MULTIVARIADA";

    private final SmartAlertService smartAlertService;
    private final AlertTypeRepository alertTypeRepository;
    private final IoTReadingRepository ioTReadingRepository;
    private final ClinicalHistoryRepository clinicalHistoryRepository;
    private final SmartAlertRepository smartAlertRepository;
    private final AiAnalysisClient aiAnalysisClient;
    private final AiServiceProperties aiProperties;

    // Regras fixas: usadas só quando o PetPulse-AI está indisponível (contingência).
    private static final int MIN_HEART_RATE = 60;
    private static final int MAX_HEART_RATE = 180;

    // 160 mmHg = 16 cmHg (escala dos exemplos antigos da API).
    private static final BigDecimal MAX_PRESSURE_MMHG =
            BigDecimal.valueOf(160);

    private static final BigDecimal CMHG_LIMIT =
            BigDecimal.valueOf(30);

    private static final BigDecimal MIN_ACTIVITY_LEVEL =
            BigDecimal.valueOf(2);

    public void analyze(IoTReading reading) {

        Pet pet = reading.getDevice().getPet();

        Optional<AiAnalysisResponse> aiResult = toAiSpecies(pet)
                .flatMap(species -> aiAnalysisClient.analyze(buildAiRequest(pet, species)));

        if (aiResult.isPresent()) {
            handleAiResult(pet, aiResult.get());
            return;
        }

        analyzeHeartRate(reading);
        analyzePressure(reading);
        analyzeActivityLevel(reading);
    }

    // =========================================================
    // Motor de IA (PetPulse-AI)
    // =========================================================

    private AiAnalysisRequest buildAiRequest(Pet pet, String species) {

        LocalDateTime since = LocalDateTime.now().minusDays(aiProperties.historyDays());

        List<IoTReading> readings = new ArrayList<>(
                ioTReadingRepository.findByDevicePetIdAndReadingDateAfter(
                        pet.getId(),
                        since,
                        PageRequest.of(0, aiProperties.maxReadings(), Sort.by(Sort.Direction.DESC, "readingDate"))
                )
        );
        Collections.reverse(readings);

        List<AiAnalysisRequest.Reading> aiReadings = readings.stream()
                .map(r -> new AiAnalysisRequest.Reading(
                        r.getReadingDate(),
                        r.getHeartRate(),
                        r.getActivityLevel(),
                        r.getPressure(),
                        r.getTemperature()
                ))
                .toList();

        List<AiAnalysisRequest.ClinicalEvent> clinicalEvents = clinicalHistoryRepository
                .findByPetIdOrderByRecordDateDesc(pet.getId())
                .stream()
                .filter(h -> h.getRecordType() != null)
                .map(h -> new AiAnalysisRequest.ClinicalEvent(
                        h.getRecordType().name(),
                        h.getDescription(),
                        h.getRecordDate(),
                        h.getReturnDate()
                ))
                .toList();

        AiAnalysisRequest.Pet aiPet = new AiAnalysisRequest.Pet(
                pet.getId(),
                pet.getName(),
                species,
                pet.getBreed() != null ? pet.getBreed().getName() : null,
                pet.getPetSize() != null ? toAiSize(pet.getPetSize().getDescription()) : null,
                ageInYears(pet.getBirthDate()),
                pet.getWeight(),
                pet.getSex() != null ? pet.getSex().name() : null
        );

        return new AiAnalysisRequest(aiPet, aiReadings, clinicalEvents, true);
    }

    private void handleAiResult(Pet pet, AiAnalysisResponse result) {

        log.info(
                "PetPulse-AI pet={} nivel={} gerarAlerta={} modo={} justificativa={}",
                pet.getId(),
                result.nivelRisco(),
                result.gerarAlerta(),
                result.modo(),
                result.justificativa()
        );

        // Severidade baixa = só registro em log (matriz de severidade da Sprint 3).
        if (!result.gerarAlerta() || result.alerta() == null || result.nivelRisco() == null) {
            return;
        }

        AlertRiskLevel riskLevel = AlertRiskLevel.valueOf(result.nivelRisco());

        if (hasRecentAiAlert(pet.getId(), riskLevel)) {
            log.info("Alerta de IA para o pet {} suprimido: já existe alerta aberto recente.", pet.getId());
            return;
        }

        AlertType alertType = alertTypeRepository
                .findByDescription(result.tipoAlerta())
                .or(() -> alertTypeRepository.findByDescription(FALLBACK_AI_ALERT_TYPE))
                .orElseThrow(() ->
                        new IllegalStateException("Tipo de alerta " + result.tipoAlerta() + " não cadastrado no banco")
                );

        smartAlertService.addSmartAlert(new SmartAlertRequest(
                pet.getId(),
                alertType.getId(),
                riskLevel,
                AlertOrigin.MOTOR_IA,
                truncate(result.alerta().mensagem(), 500),
                truncate(result.alerta().recomendacao(), 1000),
                AlertStatus.ABERTO
        ));
    }

    /**
     * Evita fadiga de alertas: no máximo um alerta de IA por pet a cada N horas enquanto a
     * anomalia persistir; dentro desse intervalo, só cria outro se o nível de risco subir.
     */
    private boolean hasRecentAiAlert(Long petId, AlertRiskLevel riskLevel) {

        LocalDateTime limit = LocalDateTime.now().minusHours(aiProperties.cooldownHours());

        return smartAlertRepository
                .findFirstByPetIdAndOriginOrderByGeneratedAtDesc(petId, AlertOrigin.MOTOR_IA)
                .filter(alert -> alert.getGeneratedAt().isAfter(limit))
                .filter(alert -> alert.getRiskLevel() != null
                        && riskLevel.ordinal() <= alert.getRiskLevel().ordinal())
                .isPresent();
    }

    /** O motor foi calibrado para cães e gatos; outras espécies seguem nas regras fixas. */
    private Optional<String> toAiSpecies(Pet pet) {

        if (pet.getSpecies() == null || pet.getSpecies().getName() == null) {
            return Optional.of("cao");
        }

        String species = normalize(pet.getSpecies().getName());

        if (species.startsWith("CACHORRO") || species.startsWith("CAO") || species.startsWith("CANINO")) {
            return Optional.of("cao");
        }

        if (species.startsWith("GATO") || species.startsWith("FELINO")) {
            return Optional.of("gato");
        }

        return Optional.empty();
    }

    private String toAiSize(String size) {

        String normalized = normalize(size);

        return switch (normalized) {
            case "PEQUENO" -> "pequeno";
            case "MEDIO" -> "medio";
            case "GRANDE" -> "grande";
            default -> null;
        };
    }

    private Double ageInYears(LocalDate birthDate) {

        if (birthDate == null) {
            return null;
        }

        Period age = Period.between(birthDate, LocalDate.now());
        return age.getYears() + age.getMonths() / 12.0;
    }

    private String normalize(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .trim()
                .toUpperCase();
    }

    private String truncate(String value, int max) {
        if (value == null || value.length() <= max) {
            return value;
        }
        return value.substring(0, max);
    }

    // =========================================================
    // Regras fixas (contingência)
    // =========================================================

    private void analyzeHeartRate(IoTReading reading) {

        Integer heartRate = reading.getHeartRate();

        if (heartRate == null) {
            return;
        }

        if (heartRate < MIN_HEART_RATE || heartRate > MAX_HEART_RATE) {
            createAlert(
                    reading,
                    "FREQUENCIA_CARDIACA",
                    AlertRiskLevel.ALTO,
                    "Frequência cardíaca fora da faixa esperada.",
                    "Recomenda-se avaliação do estado do pet e acompanhamento profissional."
            );
        }
    }

    private void analyzePressure(IoTReading reading) {

        BigDecimal pressure = reading.getPressure();

        if (pressure == null) {
            return;
        }

        // A coleira envia mmHg; valores abaixo de 30 vêm em cmHg.
        BigDecimal pressureMmHg = pressure.compareTo(CMHG_LIMIT) < 0
                ? pressure.multiply(BigDecimal.TEN)
                : pressure;

        if (pressureMmHg.compareTo(MAX_PRESSURE_MMHG) > 0) {
            createAlert(
                    reading,
                    "PRESSAO",
                    AlertRiskLevel.ALTO,
                    "Pressão detectada acima do limite configurado.",
                    "Recomenda-se acompanhar o pet e procurar avaliação profissional."
            );
        }
    }

    private void analyzeActivityLevel(IoTReading reading) {

        BigDecimal activityLevel = reading.getActivityLevel();

        if (activityLevel == null) {
            return;
        }

        if (activityLevel.compareTo(MIN_ACTIVITY_LEVEL) < 0) {
            createAlert(
                    reading,
                    "ATIVIDADE",
                    AlertRiskLevel.MEDIO,
                    "Nível de atividade abaixo do esperado.",
                    "Observe o comportamento do pet e acompanhe as próximas leituras."
            );
        }
    }

    private void createAlert(
            IoTReading reading,
            String alertTypeDescription,
            AlertRiskLevel riskLevel,
            String message,
            String recommendation
    ) {

        AlertType alertType = alertTypeRepository
                .findByDescription(alertTypeDescription)
                .orElseThrow(() ->
                        new IllegalStateException("Tipo de alerta " + alertTypeDescription + " não cadastrado no banco")
                );

        SmartAlertRequest request = new SmartAlertRequest(
                reading.getDevice().getPet().getId(),
                alertType.getId(),
                riskLevel,
                AlertOrigin.DISPOSITIVO_IOT,
                message,
                recommendation,
                AlertStatus.ABERTO
        );

        smartAlertService.addSmartAlert(request);
    }
}
