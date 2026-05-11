package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record UserRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String name,

        @CPF
        @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$|^\\d{11}$")
        @NotBlank
        String cpf,

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
        String password,

        @NotBlank
        @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$" )
        String phone,

        @NotBlank
        @Size(max = 120)
        String adress
) {
    public User toEntity(){
        return User.builder()
                .name(name)
                .cpf(cpf)
                .email(email)
                .password(password)
                .phone(phone)
                .adress(adress)
                .build();
    }

}
