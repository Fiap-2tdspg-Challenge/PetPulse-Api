package fiap.com.br.petpulse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CityRequest(

        @NotBlank(message = "O nome da cidade é obrigatório")
        @Size(max = 100, message = "O nome da cidade deve ter no máximo 100 caracteres")
        String name,

        @NotBlank(message = "O estado é obrigatório")
        @Size(min = 2, max = 2, message = "O código do estado deve ter 2 letras")
        String stateCode

) {
}
