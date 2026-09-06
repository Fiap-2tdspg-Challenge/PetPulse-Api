package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.assembler.BreedAssembler;
import fiap.com.br.petpulse.dto.request.BreedRequest;
import fiap.com.br.petpulse.dto.response.BreedResponse;
import fiap.com.br.petpulse.model.Breed;
import fiap.com.br.petpulse.model.Species;
import fiap.com.br.petpulse.repositories.BreedRepository;
import fiap.com.br.petpulse.repositories.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BreedService {

    private final BreedRepository breedRepository;
    private final SpeciesRepository speciesRepository;
    private final BreedAssembler breedAssembler;

    /**
     * Retorna a raça existente com esse nome para a espécie informada (sem
     * diferenciar maiúsculas/minúsculas) ou cria uma nova caso ainda não exista.
     */
    public BreedResponse findOrCreate(BreedRequest request) {
        Species species = findSpeciesById(request.speciesId());
        String name = request.name().trim();

        Breed breed = breedRepository.findBySpecies_IdAndNameIgnoreCase(species.getId(), name)
                .orElseGet(() -> breedRepository.save(
                        Breed.builder().species(species).name(name).build()
                ));

        return breedAssembler.toResponse(breed);
    }

    public List<BreedResponse> getBreedsBySpecies(Long speciesId) {
        return breedRepository.findBySpecies_Id(speciesId)
                .stream()
                .map(breedAssembler::toResponse)
                .toList();
    }

    private Species findSpeciesById(Long id) {
        return speciesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Espécie com id " + id + " não encontrada"
                ));
    }
}
