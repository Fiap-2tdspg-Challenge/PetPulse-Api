package fiap.com.br.petpulse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SpeciesRequest(

        @NotBlank(message = "O nome da espécie é obrigatório")
        @Size(max = 50, message = "O nome da espécie deve ter no máximo 50 caracteres")
        String name

) {
}
