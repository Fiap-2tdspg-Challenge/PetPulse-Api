package fiap.com.br.petpulse.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProfessionalRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
        String name,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(max = 255, message = "A senha deve ter no máximo 255 caracteres")
        String password,

        @NotBlank(message = "O CRMV é obrigatório")
        @Size(max = 20, message = "O CRMV deve ter no máximo 20 caracteres")
        String crmv,

        @NotNull(message = "A clínica é obrigatória")
        @Positive(message = "O ID da clínica deve ser maior que zero")
        Long clinicId

) {
}
