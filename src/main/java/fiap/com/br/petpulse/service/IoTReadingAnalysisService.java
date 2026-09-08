package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.request.SmartAlertRequest;
import fiap.com.br.petpulse.enums.AlertOrigin;
import fiap.com.br.petpulse.enums.AlertRiskLevel;
import fiap.com.br.petpulse.enums.AlertStatus;
import fiap.com.br.petpulse.model.AlertType;
import fiap.com.br.petpulse.model.IoTReading;
import fiap.com.br.petpulse.repositories.AlertTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class IoTReadingAnalysisService {

    private final SmartAlertService smartAlertService;
    private final AlertTypeRepository alertTypeRepository;

    public void analyze(IoTReading reading) {

        analyzeHeartRate(reading);
        analyzePressure(reading);
        analyzeActivityLevel(reading);
    }

    private void analyzeHeartRate(IoTReading reading) {

        Integer heartRate = reading.getHeartRate();

        if (heartRate == null) {
            return;
        }

        if (heartRate < 60 || heartRate > 180) {
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

        if (pressure.compareTo(BigDecimal.valueOf(160)) > 0) {
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

        if (activityLevel.compareTo(BigDecimal.valueOf(10)) < 0) {
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
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Tipo de alerta "
                                        + alertTypeDescription
                                        + " não encontrado"
                        )
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