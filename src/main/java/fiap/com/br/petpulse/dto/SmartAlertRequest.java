package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.enums.AlertOrigin;
import fiap.com.br.petpulse.enums.AlertRiskLevel;
import fiap.com.br.petpulse.enums.AlertStatus;
import fiap.com.br.petpulse.enums.AlertType;
import fiap.com.br.petpulse.model.SmartAlert;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SmartAlertRequest(

        @NotNull(message = "Tipo do Alerta é obrigatorio")
        AlertType alertType,

        @NotNull(message = "Tipo do Risco é obrigatorio")
        AlertRiskLevel riskLevel,

        @NotNull(message = "Origem do Alerta é obrigatoria")
        AlertOrigin alertOrigin,

        @NotBlank(message = "Mensagem é obrigatória")
        @Size(max = 500)
        String message,

        @Size(max = 1000)
        String recommendation,

        @NotNull(message = "Pet id é requerido")
        Long petId
) {
    public SmartAlert toEntity(){
        return SmartAlert.builder()
                .alertType(alertType)
                .riskLevel(riskLevel)
                .alertOrigin(alertOrigin)
                .message(message)
                .recommendation(recommendation)
                .build();
    }
}
