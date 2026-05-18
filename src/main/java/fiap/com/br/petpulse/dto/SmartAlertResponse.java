package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.enums.AlertOrigin;
import fiap.com.br.petpulse.enums.AlertRiskLevel;
import fiap.com.br.petpulse.enums.AlertStatus;
import fiap.com.br.petpulse.enums.AlertType;
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
    public static SmartAlertResponse toResponse(SmartAlert smartAlert){
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
