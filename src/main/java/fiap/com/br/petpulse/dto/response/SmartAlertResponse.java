package fiap.com.br.petpulse.dto.response;

import fiap.com.br.petpulse.enums.AlertOrigin;
import fiap.com.br.petpulse.enums.AlertRiskLevel;
import fiap.com.br.petpulse.enums.AlertStatus;

import java.time.LocalDateTime;

public record SmartAlertResponse(

        Long id,

        Long petId,
        String petName,

        Long alertTypeId,
        String alertTypeDescription,

        AlertRiskLevel riskLevel,

        AlertOrigin origin,

        String message,

        String recommendation,

        LocalDateTime generatedAt,

        AlertStatus status

) {
}