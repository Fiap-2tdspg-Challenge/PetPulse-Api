package fiap.com.br.petpulse.assembler;

import fiap.com.br.petpulse.dto.response.SpeciesResponse;
import fiap.com.br.petpulse.model.Species;
import org.springframework.stereotype.Component;

@Component
public class SpeciesAssembler {

    public SpeciesResponse toResponse(Species species) {
        return new SpeciesResponse(
                species.getId(),
                species.getName()
        );
    }
}
