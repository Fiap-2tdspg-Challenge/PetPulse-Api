package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.ai.AiAnalysisClient;
import fiap.com.br.petpulse.ai.AiAnalysisRequest;
import fiap.com.br.petpulse.ai.AiAnalysisResponse;
import fiap.com.br.petpulse.config.AiServiceProperties;
import fiap.com.br.petpulse.dto.request.SmartAlertRequest;
import fiap.com.br.petpulse.enums.AlertOrigin;
import fiap.com.br.petpulse.enums.AlertRiskLevel;
import fiap.com.br.petpulse.enums.AlertStatus;
import fiap.com.br.petpulse.enums.RecordType;
import fiap.com.br.petpulse.enums.Sex;
import fiap.com.br.petpulse.model.AlertType;
import fiap.com.br.petpulse.model.Breed;
import fiap.com.br.petpulse.model.ClinicalHistory;
import fiap.com.br.petpulse.model.IoTDevice;
import fiap.com.br.petpulse.model.IoTReading;
import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.model.PetSize;
import fiap.com.br.petpulse.model.SmartAlert;
import fiap.com.br.petpulse.model.Species;
import fiap.com.br.petpulse.repositories.AlertTypeRepository;
import fiap.com.br.petpulse.repositories.ClinicalHistoryRepository;
import fiap.com.br.petpulse.repositories.IoTReadingRepository;
import fiap.com.br.petpulse.repositories.SmartAlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class IoTReadingAnalysisServiceTest {

    @Mock
    private SmartAlertService smartAlertService;

    @Mock
    private AlertTypeRepository alertTypeRepository;

    @Mock
    private IoTReadingRepository ioTReadingRepository;

    @Mock
    private ClinicalHistoryRepository clinicalHistoryRepository;

    @Mock
    private SmartAlertRepository smartAlertRepository;

    @Mock
    private AiAnalysisClient aiAnalysisClient;

    private IoTReadingAnalysisService service;

    private Pet pet;

    private IoTReading reading;

    @BeforeEach
    void setUp() {
        AiServiceProperties properties =
                new AiServiceProperties(true, "http://localhost:8000", "", 1000, 14, 3000, 6, 60);

        service = new IoTReadingAnalysisService(
                smartAlertService,
                alertTypeRepository,
                ioTReadingRepository,
                clinicalHistoryRepository,
                smartAlertRepository,
                aiAnalysisClient,
                properties
        );

        pet = Pet.builder()
                .id(1L)
                .name("Thor")
                .species(Species.builder().id(1L).name("CACHORRO").build())
                .breed(Breed.builder().id(2L).name("Labrador").build())
                .petSize(PetSize.builder().id(3L).description("GRANDE").build())
                .birthDate(LocalDate.now().minusYears(6))
                .weight(new BigDecimal("32.0"))
                .sex(Sex.M)
                .neutered(true)
                .build();

        reading = IoTReading.builder()
                .id(10L)
                .device(IoTDevice.builder().id(5L).pet(pet).build())
                .readingDate(LocalDateTime.now())
                .heartRate(107)
                .activityLevel(new BigDecimal("9.5"))
                .pressure(new BigDecimal("132"))
                .temperature(new BigDecimal("38.5"))
                .build();

        when(ioTReadingRepository.findByDevicePetIdAndReadingDateAfter(eq(1L), any(), any()))
                .thenReturn(List.of(reading));

        when(clinicalHistoryRepository.findByPetIdOrderByRecordDateDesc(1L))
                .thenReturn(List.of(ClinicalHistory.builder()
                        .recordType(RecordType.VACINA)
                        .description("V10")
                        .recordDate(LocalDate.now().minusDays(1))
                        .build()));

        when(alertTypeRepository.findByDescription("FREQUENCIA_CARDIACA"))
                .thenReturn(Optional.of(AlertType.builder().id(1L).description("FREQUENCIA_CARDIACA").build()));

        when(smartAlertRepository.findFirstByPetIdAndOriginOrderByGeneratedAtDesc(1L, AlertOrigin.MOTOR_IA))
                .thenReturn(Optional.empty());
    }

    private AiAnalysisResponse aiResponse(String riskLevel, boolean createAlert) {
        return new AiAnalysisResponse(
                riskLevel,
                createAlert,
                "FREQUENCIA_CARDIACA",
                "baseline_individual",
                0.92,
                List.of("Desvio persistente em frequencia_cardiaca → MEDIO."),
                new AiAnalysisResponse.Alert(
                        "Mudança no padrão de frequência cardíaca do Thor",
                        "A frequência cardíaca do Thor está 31% acima do padrão dele em repouso há cerca de 6 h.",
                        "Observe o Thor e agende uma consulta de rotina com o veterinário.",
                        "gemini"
                )
        );
    }

    @Test
    void createsAiAlertWhenEngineFindsAnomaly() {
        when(aiAnalysisClient.analyze(any())).thenReturn(Optional.of(aiResponse("MEDIO", true)));

        service.analyze(reading);

        ArgumentCaptor<SmartAlertRequest> alert = ArgumentCaptor.forClass(SmartAlertRequest.class);
        verify(smartAlertService).addSmartAlert(alert.capture());
        assertThat(alert.getValue().origin()).isEqualTo(AlertOrigin.MOTOR_IA);
        assertThat(alert.getValue().riskLevel()).isEqualTo(AlertRiskLevel.MEDIO);
        assertThat(alert.getValue().status()).isEqualTo(AlertStatus.ABERTO);
        assertThat(alert.getValue().message()).contains("Thor").contains("31%");
        assertThat(alert.getValue().alertTypeId()).isEqualTo(1L);
    }

    @Test
    void sendsPetProfileReadingsAndClinicalHistoryToTheAi() {
        when(aiAnalysisClient.analyze(any())).thenReturn(Optional.of(aiResponse(null, false)));

        service.analyze(reading);

        ArgumentCaptor<AiAnalysisRequest> request = ArgumentCaptor.forClass(AiAnalysisRequest.class);
        verify(aiAnalysisClient).analyze(request.capture());
        AiAnalysisRequest sent = request.getValue();
        assertThat(sent.pet().especie()).isEqualTo("cao");
        assertThat(sent.pet().porte()).isEqualTo("grande");
        assertThat(sent.pet().raca()).isEqualTo("Labrador");
        assertThat(sent.pet().idadeAnos()).isBetween(5.9, 6.1);
        assertThat(sent.pet().sexo()).isEqualTo("M");
        assertThat(sent.leituras()).hasSize(1);
        assertThat(sent.leituras().get(0).temperatura()).isEqualByComparingTo("38.5");
        assertThat(sent.historicoClinico()).extracting(AiAnalysisRequest.ClinicalEvent::tipo).containsExactly("VACINA");
    }

    @Test
    void doesNotCreateAlertWhenEngineOnlyLogs() {
        when(aiAnalysisClient.analyze(any())).thenReturn(Optional.of(aiResponse("BAIXO", false)));

        service.analyze(reading);

        verify(smartAlertService, never()).addSmartAlert(any());
    }

    @Test
    void doesNotDuplicateRecentAiAlertWithSameRisk() {
        when(aiAnalysisClient.analyze(any())).thenReturn(Optional.of(aiResponse("MEDIO", true)));
        when(smartAlertRepository.findFirstByPetIdAndOriginOrderByGeneratedAtDesc(1L, AlertOrigin.MOTOR_IA))
                .thenReturn(Optional.of(SmartAlert.builder()
                        .riskLevel(AlertRiskLevel.MEDIO)
                        .origin(AlertOrigin.MOTOR_IA)
                        .status(AlertStatus.ABERTO)
                        .generatedAt(LocalDateTime.now().minusHours(2))
                        .build()));

        service.analyze(reading);

        verify(smartAlertService, never()).addSmartAlert(any());
    }

    @Test
    void createsNewAlertWhenRiskEscalates() {
        when(aiAnalysisClient.analyze(any())).thenReturn(Optional.of(aiResponse("ALTO", true)));
        when(smartAlertRepository.findFirstByPetIdAndOriginOrderByGeneratedAtDesc(1L, AlertOrigin.MOTOR_IA))
                .thenReturn(Optional.of(SmartAlert.builder()
                        .riskLevel(AlertRiskLevel.MEDIO)
                        .origin(AlertOrigin.MOTOR_IA)
                        .status(AlertStatus.ABERTO)
                        .generatedAt(LocalDateTime.now().minusHours(2))
                        .build()));

        service.analyze(reading);

        verify(smartAlertService).addSmartAlert(any());
    }

    @Test
    void fallsBackToFixedRulesWhenAiIsUnavailable() {
        when(aiAnalysisClient.analyze(any())).thenReturn(Optional.empty());
        reading.setHeartRate(200);

        service.analyze(reading);

        ArgumentCaptor<SmartAlertRequest> alert = ArgumentCaptor.forClass(SmartAlertRequest.class);
        // só a FC dispara: 132 mmHg está abaixo de 160 mmHg e a atividade (9,5) está acima de 2
        verify(smartAlertService).addSmartAlert(alert.capture());
        assertThat(alert.getValue().origin()).isEqualTo(AlertOrigin.DISPOSITIVO_IOT);
        assertThat(alert.getValue().riskLevel()).isEqualTo(AlertRiskLevel.ALTO);
    }

    @Test
    void unsupportedSpeciesSkipsAiAndUsesFixedRules() {
        pet.setSpecies(Species.builder().id(3L).name("COELHO").build());
        reading.setHeartRate(200);

        service.analyze(reading);

        verify(aiAnalysisClient, never()).analyze(any());
        verify(smartAlertService).addSmartAlert(any());
    }
}
