package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.enums.PetSize;
import fiap.com.br.petpulse.enums.Sex;
import fiap.com.br.petpulse.model.Pet;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PetRequest(

        @NotBlank
        @Size(min = 3, max = 50)
        String name,

        @NotBlank
        String species,

        @NotBlank
        String breed,

        @NotNull
        @Past
        LocalDate birthDate,

        @NotNull
        @Positive(message = "O peso tem que ser maior que zero.")
        Double weight,

        @NotNull
        Sex sex,

        boolean neutered,

        @NotNull
        PetSize size,

        String knownDiseases,

        @NotNull(message = "Tutor id é requerido")
        Long tutorId

) {
    public Pet toEntity(){
        return Pet.builder()
                .name(name)
                .species(species)
                .breed(breed)
                .birthDate(birthDate)
                .weight(weight)
                .sex(sex)
                .neutered(neutered)
                .petSize(size)
                .knownDiseases(knownDiseases)
                .build();
    }
}
