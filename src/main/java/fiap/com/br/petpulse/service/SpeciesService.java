package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.assembler.SpeciesAssembler;
import fiap.com.br.petpulse.dto.request.SpeciesRequest;
import fiap.com.br.petpulse.dto.response.SpeciesResponse;
import fiap.com.br.petpulse.model.Species;
import fiap.com.br.petpulse.repositories.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeciesService {

    private final SpeciesRepository speciesRepository;
    private final SpeciesAssembler speciesAssembler;

    /**
     * Retorna a espécie existente com esse nome (sem diferenciar maiúsculas/minúsculas)
     * ou cria uma nova caso ainda não exista.
     */
    public SpeciesResponse findOrCreate(SpeciesRequest request) {
        String name = request.name().trim();

        Species species = speciesRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> speciesRepository.save(
                        Species.builder().name(name).build()
                ));

        return speciesAssembler.toResponse(species);
    }

    public List<SpeciesResponse> getAllSpecies() {
        return speciesRepository.findAll()
                .stream()
                .map(speciesAssembler::toResponse)
                .toList();
    }
}
