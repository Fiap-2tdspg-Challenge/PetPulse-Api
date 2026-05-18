package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.PetRequest;
import fiap.com.br.petpulse.dto.PetResponse;
import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.model.Tutor;
import fiap.com.br.petpulse.repositories.PetRepository;
import fiap.com.br.petpulse.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    public PetResponse addPet(PetRequest request) {

        Tutor tutor = tutorRepository.findById(request.tutorId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tutor com id " + request.tutorId() + " não encontrado"
                )
        );

        Pet pet = request.toEntity();
        pet.setTutor(tutor);

        return PetResponse.toResponse(petRepository.save(pet));
    }

    public Page<PetResponse> getAllPets(Pageable pageable) {
        return petRepository.findAll(pageable)
                .map(PetResponse::toResponse);
    }

    public PetResponse getPetById(Long id) {
        return PetResponse.toResponse(findPetById(id));
    }

    public void deletePet(Long id) {
        findPetById(id);
        petRepository.deleteById(id);
    }

    public PetResponse updatePet(Long id, PetRequest request) {

        Pet pet = findPetById(id);

        Tutor tutor = tutorRepository.findById(request.tutorId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tutor com id " + request.tutorId() + " não encontrado"
                )
        );

        pet.setName(request.name());
        pet.setSpecies(request.species());
        pet.setBreed(request.breed());
        pet.setBirthDate(request.birthDate());
        pet.setWeight(request.weight());
        pet.setSex(request.sex());
        pet.setNeutered(request.neutered());
        pet.setPetSize(request.size());
        pet.setKnownDiseases(request.knownDiseases());
        pet.setTutor(tutor);

        return PetResponse.toResponse(petRepository.save(pet));
    }

    private Pet findPetById(Long id) {
        return petRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pet com id " + id + " não encontrado"
                )
        );
    }

    public List<PetResponse> searchPetsByName(String name) {
        return petRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(PetResponse::toResponse)
                .toList();
    }
}