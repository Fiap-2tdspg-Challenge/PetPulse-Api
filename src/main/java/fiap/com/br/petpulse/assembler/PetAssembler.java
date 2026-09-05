package fiap.com.br.petpulse.assembler;

import fiap.com.br.petpulse.dto.request.PetRequest;
import fiap.com.br.petpulse.dto.response.PetResponse;
import fiap.com.br.petpulse.model.*;
import org.springframework.stereotype.Component;

@Component
public class PetAssembler {

    public Pet toEntity(
            PetRequest request,
            Tutor tutor,
            Species species,
            Breed breed,
            PetSize petSize
    ) {
        return Pet.builder()
                .name(request.name())
                .birthDate(request.birthDate())
                .weight(request.weight())
                .sex(request.sex())
                .neutered(request.neutered())
                .tutor(tutor)
                .species(species)
                .breed(breed)
                .petSize(petSize)
                .build();
    }

    public PetResponse toResponse(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getBirthDate(),
                pet.getWeight(),
                pet.getSex(),
                pet.getNeutered(),

                pet.getTutor().getId(),
                pet.getTutor().getName(),

                pet.getSpecies().getId(),
                pet.getSpecies().getName(),

                pet.getBreed().getId(),
                pet.getBreed().getName(),

                pet.getPetSize().getId(),
                pet.getPetSize().getDescription(),

                pet.getCreatedAt()
        );
    }

    public void updateEntity(
            Pet pet,
            PetRequest request,
            Tutor tutor,
            Species species,
            Breed breed,
            PetSize petSize
    ) {
        pet.setName(request.name());
        pet.setBirthDate(request.birthDate());
        pet.setWeight(request.weight());
        pet.setSex(request.sex());
        pet.setNeutered(request.neutered());

        pet.setTutor(tutor);
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setPetSize(petSize);
    }
}