package fiap.com.br.petpulse.dto.request;

import fiap.com.br.petpulse.enums.AlertOrigin;
import fiap.com.br.petpulse.enums.AlertRiskLevel;
import fiap.com.br.petpulse.enums.AlertStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record SmartAlertRequest(

        @NotNull(message = "O pet é obrigatório")
        @Positive(message = "O ID do pet deve ser maior que zero")
        Long petId,

        @NotNull(message = "O tipo de alerta é obrigatório")
        @Positive(message = "O ID do tipo de alerta deve ser maior que zero")
        Long alertTypeId,

        AlertRiskLevel riskLevel,

        AlertOrigin origin,

        @Size(max = 500, message = "A mensagem deve ter no máximo 500 caracteres")
        String message,

        @Size(max = 1000, message = "A recomendação deve ter no máximo 1000 caracteres")
        String recommendation,

        @NotNull(message = "O status é obrigatório")
        AlertStatus status

) {
}