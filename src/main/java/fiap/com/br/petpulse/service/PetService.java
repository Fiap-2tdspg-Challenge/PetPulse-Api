package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet addPet(Pet pet){
        return petRepository.save(pet);
    }

    public List<Pet> getAllUPets() {
        return petRepository.findAll();
    }

    public Pet getPetById(Long id){
        return findPetById(id);
    }

    public void deletePet(Long id) {
        findPetById(id);
        petRepository.deleteById(id);
    }

    public Pet updatePet(Long id, Pet newPet) {
        findPetById(id);
        newPet.setId(id);
        return petRepository.save(newPet);
    }

    private Pet findPetById(Long id) {
        return petRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Petcom id " + id + " não encontrado")
        );
    }
}
