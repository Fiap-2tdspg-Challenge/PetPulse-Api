package fiap.com.br.petpulse.dto.response;

import fiap.com.br.petpulse.enums.Sex;
import fiap.com.br.petpulse.model.Pet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PetResponse(

        Long id,
        String name,
        LocalDate birthDate,
        BigDecimal weight,
        Sex sex,
        Boolean neutered,

        Long tutorId,
        String tutorName,

        Long speciesId,
        String speciesName,

        Long breedId,
        String breedName,

        Long petSizeId,
        String petSizeDescription,

        LocalDateTime createdAt

) {}
