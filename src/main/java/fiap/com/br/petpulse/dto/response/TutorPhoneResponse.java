package fiap.com.br.petpulse.dto.response;

import fiap.com.br.petpulse.model.TutorPhone;

public record TutorPhoneResponse(
        Long id,
        Long tutorId,
        String phoneNumber
) {
    public static TutorPhoneResponse toResponse(TutorPhone phone) {
        return new TutorPhoneResponse(
                phone.getId(),
                phone.getTutor().getId(),
                phone.getPhoneNumber()
        );
    }
}
