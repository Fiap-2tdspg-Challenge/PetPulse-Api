package fiap.com.br.petpulse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BreedRequest(

        @NotNull(message = "A espécie é obrigatória")
        @Positive(message = "O ID da espécie deve ser maior que zero")
        Long speciesId,

        @NotBlank(message = "O nome da raça é obrigatório")
        @Size(max = 100, message = "O nome da raça deve ter no máximo 100 caracteres")
        String name

) {
}
