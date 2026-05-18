package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.PetRequest;
import fiap.com.br.petpulse.dto.PetResponse;
import fiap.com.br.petpulse.service.PetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@Slf4j
@Tag(name = "Pet", description = "Endpoint para gerenciamento de Pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetResponse addPet(@RequestBody @Valid PetRequest request) {
        return petService.addPet(request);
    }

    @GetMapping
    public Page<PetResponse> getAllPets(Pageable pageable) {
        return petService.getAllPets(pageable);
    }

    @GetMapping("/{id}")
    public PetResponse getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @PutMapping("/{id}")
    public PetResponse updatePet(@PathVariable Long id, @RequestBody @Valid PetRequest request
    ) {
        return petService.updatePet(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePet(@PathVariable Long id) {
        petService.deletePet(id);
    }

    @GetMapping("/search")
    public List<PetResponse> searchPetsByName(@RequestParam String name) {
        return petService.searchPetsByName(name);
    }
}