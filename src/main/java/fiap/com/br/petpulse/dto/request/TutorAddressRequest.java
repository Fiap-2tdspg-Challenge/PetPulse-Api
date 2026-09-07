package fiap.com.br.petpulse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TutorAddressRequest(

        @NotNull(message = "O tutor é obrigatório")
        @Positive(message = "O ID do tutor deve ser maior que zero")
        Long tutorId,

        @NotNull(message = "O tipo de endereço é obrigatório")
        @Positive(message = "O ID do tipo de endereço deve ser maior que zero")
        Integer addressTypeId,

        @NotNull(message = "A cidade é obrigatória")
        @Positive(message = "O ID da cidade deve ser maior que zero")
        Long cityId,

        @NotBlank(message = "O endereço é obrigatório")
        @Size(max = 150, message = "O endereço deve ter no máximo 150 caracteres")
        String address,

        @Size(max = 20, message = "O número deve ter no máximo 20 caracteres")
        String number,

        @Size(max = 100, message = "O complemento deve ter no máximo 100 caracteres")
        String complement,

        @Size(max = 9, message = "O CEP deve ter no máximo 9 caracteres")
        String zipCode,

        @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
        String neighborhood

) {
}
