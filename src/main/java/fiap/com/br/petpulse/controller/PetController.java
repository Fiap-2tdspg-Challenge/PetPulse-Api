package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public Pet addPet(@RequestBody @Valid Pet pet) {
        return petService.addPet(pet);
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllUPets();
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @PutMapping("/{id}")
    public Pet updatePet(
            @PathVariable Long id,
            @RequestBody @Valid Pet pet
    ) {
        return petService.updatePet(id, pet);
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Long id) {
        petService.deletePet(id);
    }
}
