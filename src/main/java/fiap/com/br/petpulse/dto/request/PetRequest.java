package fiap.com.br.petpulse.dto.request;

import fiap.com.br.petpulse.enums.Sex;
import fiap.com.br.petpulse.model.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PetRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100)
        String name,

        @NotNull
        @Past
        LocalDate birthDate,

        @NotNull
        @Positive(message = "O peso tem que ser maior que zero.")
        BigDecimal weight,

        @NotNull(message = "O sexo é obrigatório")
        Sex sex,

        @NotNull(message = "Informe se o pet é castrado")
        Boolean neutered,

        @NotNull(message = "O tutor é obrigatório")
        Long tutorId,

        @NotNull(message = "A espécie é obrigatória")
        Long speciesId,

        @NotNull(message = "A raça é obrigatória")
        Long breedId,

        @NotNull(message = "O porte é obrigatório")
        Long petSizeId
) {}
