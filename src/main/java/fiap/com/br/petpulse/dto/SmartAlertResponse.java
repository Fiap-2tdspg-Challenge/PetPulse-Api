package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.Enums.AlertOrigin;
import fiap.com.br.petpulse.Enums.AlertRiskLevel;
import fiap.com.br.petpulse.Enums.AlertStatus;
import fiap.com.br.petpulse.Enums.AlertType;
import fiap.com.br.petpulse.model.SmartAlert;

import java.time.LocalDate;

public record SmartAlertResponse(
        Long id,

        AlertType alertType,

        AlertRiskLevel riskLevel,

        AlertOrigin alertOrigin,

        String message,

        String recommendation,

        LocalDate generatedAt,

        AlertStatus status
) {
    public static SmartAlertResponse fromEntity(SmartAlert smartAlert){
        return new SmartAlertResponse(
                smartAlert.getId(),
                smartAlert.getAlertType(),
                smartAlert.getRiskLevel(),
                smartAlert.getAlertOrigin(),
                smartAlert.getMessage(),
                smartAlert.getRecommendation(),
                smartAlert.getGeneratedAt(),
                smartAlert.getStatus()
        );
    }
}
