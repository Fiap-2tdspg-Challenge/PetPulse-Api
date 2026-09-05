package fiap.com.br.petpulse.assembler;

import fiap.com.br.petpulse.dto.request.SmartAlertRequest;
import fiap.com.br.petpulse.dto.response.SmartAlertResponse;
import fiap.com.br.petpulse.model.AlertType;
import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.model.SmartAlert;
import org.springframework.stereotype.Component;

@Component
public class SmartAlertAssembler {

    public SmartAlert toEntity(
            SmartAlertRequest request,
            Pet pet,
            AlertType alertType
    ) {
        return SmartAlert.builder()
                .pet(pet)
                .alertType(alertType)
                .riskLevel(request.riskLevel())
                .origin(request.origin())
                .message(request.message())
                .recommendation(request.recommendation())
                .status(request.status())
                .build();
    }

    public SmartAlertResponse toResponse(SmartAlert alert) {
        return new SmartAlertResponse(
                alert.getId(),

                alert.getPet().getId(),
                alert.getPet().getName(),

                alert.getAlertType().getId(),
                alert.getAlertType().getDescription(),

                alert.getRiskLevel(),
                alert.getOrigin(),
                alert.getMessage(),
                alert.getRecommendation(),
                alert.getGeneratedAt(),
                alert.getStatus()
        );
    }

    public void updateEntity(
            SmartAlert alert,
            SmartAlertRequest request,
            Pet pet,
            AlertType alertType
    ) {
        alert.setPet(pet);
        alert.setAlertType(alertType);
        alert.setRiskLevel(request.riskLevel());
        alert.setOrigin(request.origin());
        alert.setMessage(request.message());
        alert.setRecommendation(request.recommendation());
        alert.setStatus(request.status());
    }
}