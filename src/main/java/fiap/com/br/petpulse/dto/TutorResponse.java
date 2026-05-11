package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.model.Tutor;

public record TutorResponse(
        Long id,
        String name,
        String cpf,
        String email,
        String password,
        String phone,
        String adress
) {
    public static TutorResponse fromEntity(Tutor tutor){
        return new TutorResponse(
                tutor.getId(),
                tutor.getName(),
                tutor.getCpf(),
                tutor.getEmail(),
                tutor.getPassword(),
                tutor.getPhone(),
                tutor.getAdress()
        );
    }
}
