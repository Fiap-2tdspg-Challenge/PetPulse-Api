package fiap.com.br.petpulse.assembler;

import fiap.com.br.petpulse.dto.response.BreedResponse;
import fiap.com.br.petpulse.model.Breed;
import org.springframework.stereotype.Component;

@Component
public class BreedAssembler {

    public BreedResponse toResponse(Breed breed) {
        return new BreedResponse(
                breed.getId(),
                breed.getSpecies().getId(),
                breed.getSpecies().getName(),
                breed.getName()
        );
    }
}
