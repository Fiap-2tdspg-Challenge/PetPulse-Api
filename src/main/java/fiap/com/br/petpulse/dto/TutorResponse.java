package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.model.Tutor;

import java.time.LocalDateTime;

public record TutorResponse(
        Long id,
        String name,
        String cpf,
        String email,
        String phone,
        String address,
        LocalDateTime createdAt
) {
    public static TutorResponse toResponse(Tutor tutor){
        return new TutorResponse(
                tutor.getId(),
                tutor.getName(),
                tutor.getCpf(),
                tutor.getEmail(),
                tutor.getPhone(),
                tutor.getAddress(),
                tutor.getCreatedAt()
        );
    }
}
