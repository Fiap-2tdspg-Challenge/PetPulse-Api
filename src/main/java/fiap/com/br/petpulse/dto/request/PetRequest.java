package fiap.com.br.petpulse.dto.request;

import fiap.com.br.petpulse.enums.Sex;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PetRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100)
        String name,

        @NotNull
        @PastOrPresent(message = "A data de nascimento não pode estar no futuro")
        LocalDate birthDate,

        @NotNull
        @Positive(message = "O peso tem que ser maior que zero.")
        BigDecimal weight,

        @NotNull(message = "O sexo é obrigatório")
        Sex sex,

        @NotNull(message = "Informe se o pet é castrado")
        Boolean neutered,

        @NotNull(message = "O tutor é obrigatório")
        @Positive(message = "O ID do tutor deve ser maior que zero")
        Long tutorId,

        @NotNull(message = "A espécie é obrigatória")
        @Positive(message = "O ID da espécie deve ser maior que zero")
        Long speciesId,

        @NotNull(message = "A raça é obrigatória")
        @Positive(message = "O ID da raça deve ser maior que zero")
        Long breedId,

        @NotNull(message = "O porte é obrigatório")
        @Positive(message = "O ID do porte deve ser maior que zero")
        Long petSizeId
) {}
