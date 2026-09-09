package fiap.com.br.petpulse.dto.response;

import fiap.com.br.petpulse.model.Professional;

import java.time.LocalDateTime;

public record ProfessionalResponse(
        Long id,
        String name,
        String email,
        String crmv,
        Long clinicId,
        String clinicName,
        LocalDateTime createdAt
) {
    public static ProfessionalResponse toResponse(Professional professional) {
        return new ProfessionalResponse(
                professional.getId(),
                professional.getName(),
                professional.getEmail(),
                professional.getCrmv(),
                professional.getClinic().getId(),
                professional.getClinic().getName(),
                professional.getCreatedAt()
        );
    }
}
