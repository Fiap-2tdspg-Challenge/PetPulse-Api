package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.assembler.PetAssembler;
import fiap.com.br.petpulse.dto.request.PetRequest;
import fiap.com.br.petpulse.dto.response.PetResponse;
import fiap.com.br.petpulse.model.Breed;
import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.model.PetSize;
import fiap.com.br.petpulse.model.Species;
import fiap.com.br.petpulse.model.Tutor;
import fiap.com.br.petpulse.repositories.BreedRepository;
import fiap.com.br.petpulse.repositories.PetRepository;
import fiap.com.br.petpulse.repositories.PetSizeRepository;
import fiap.com.br.petpulse.repositories.SpeciesRepository;
import fiap.com.br.petpulse.repositories.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "pets")
public class PetService {

    private final PetRepository petRepository;
    private final TutorRepository tutorRepository;
    private final SpeciesRepository speciesRepository;
    private final BreedRepository breedRepository;
    private final PetSizeRepository petSizeRepository;
    private final PetAssembler petAssembler;


    @CacheEvict(allEntries = true)
    public PetResponse addPet(PetRequest request) {

        Tutor tutor = findTutorById(request.tutorId());
        Species species = findSpeciesById(request.speciesId());
        Breed breed = findBreedById(request.breedId());
        PetSize petSize = findPetSizeById(request.petSizeId());

        Pet pet = petAssembler.toEntity(
                request,
                tutor,
                species,
                breed,
                petSize
        );

        return petAssembler.toResponse(
                petRepository.save(pet)
        );
    }


    @Cacheable
    public Page<PetResponse> getAllPets(Pageable pageable) {
        return petRepository.findAll(pageable)
                .map(petAssembler::toResponse);
    }


    @Cacheable
    public PetResponse getPetById(Long id) {
        return petAssembler.toResponse(
                findPetById(id)
        );
    }


    @CacheEvict(allEntries = true)
    public void deletePet(Long id) {

        Pet pet = findPetById(id);

        petRepository.delete(pet);
    }


    @CacheEvict(allEntries = true)
    public PetResponse updatePet(Long id, PetRequest request) {

        Pet pet = findPetById(id);

        Tutor tutor = findTutorById(request.tutorId());
        Species species = findSpeciesById(request.speciesId());
        Breed breed = findBreedById(request.breedId());
        PetSize petSize = findPetSizeById(request.petSizeId());

        petAssembler.updateEntity(
                pet,
                request,
                tutor,
                species,
                breed,
                petSize
        );

        return petAssembler.toResponse(
                petRepository.save(pet)
        );
    }


    @Cacheable
    public List<PetResponse> searchPetsByName(String name) {
        return petRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(petAssembler::toResponse)
                .toList();
    }


    private Pet findPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pet com id " + id + " não encontrado"
                ));
    }


    private Tutor findTutorById(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tutor com id " + id + " não encontrado"
                ));
    }


    private Species findSpeciesById(Long id) {
        return speciesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Espécie com id " + id + " não encontrada"
                ));
    }


    private Breed findBreedById(Long id) {
        return breedRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Raça com id " + id + " não encontrada"
                ));
    }


    private PetSize findPetSizeById(Long id) {
        return petSizeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Porte com id " + id + " não encontrado"
                ));
    }
}