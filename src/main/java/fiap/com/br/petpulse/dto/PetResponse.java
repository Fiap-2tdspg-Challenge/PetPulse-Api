package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.enums.PetSize;
import fiap.com.br.petpulse.enums.Sex;
import fiap.com.br.petpulse.model.Pet;

import java.time.LocalDate;

public record PetResponse(

        Long id,
        String name,
        String species,
        String breed,
        LocalDate birthDate,
        Double weight,
        Sex sex,
        boolean neutered,
        PetSize size,
        String knownDiseases,

        Long tutorId,
        String tutorName

) {
    public static PetResponse toResponse(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getBirthDate(),
                pet.getWeight(),
                pet.getSex(),
                pet.isNeutered(),
                pet.getPetSize(),
                pet.getKnownDiseases(),
                pet.getTutor().getId(),
                pet.getTutor().getName()
        );
    }
}
