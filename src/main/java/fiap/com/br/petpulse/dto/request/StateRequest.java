package fiap.com.br.petpulse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StateRequest(

        @NotBlank(message = "O código do estado é obrigatório")
        @Size(min = 2, max = 2, message = "O código do estado deve ter 2 letras")
        String code,

        @NotBlank(message = "O nome do estado é obrigatório")
        @Size(max = 50, message = "O nome do estado deve ter no máximo 50 caracteres")
        String name

) {
}
