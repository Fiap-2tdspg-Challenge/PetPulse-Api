package fiap.com.br.petpulse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TutorPhoneRequest(

        @NotNull(message = "O tutor é obrigatório")
        @Positive(message = "O ID do tutor deve ser maior que zero")
        Long tutorId,

        @NotBlank(message = "O número do telefone é obrigatório")
        @Size(max = 20, message = "O número do telefone deve ter no máximo 20 caracteres")
        String phoneNumber

) {
}
