package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.PetRequest;
import fiap.com.br.petpulse.dto.PetResponse;
import fiap.com.br.petpulse.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Pet", description = "Endpoints para gerenciamento dos pets cadastrados na carteira digital PetPulse")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    @Operation(
            summary = "Cadastrar novo pet",
            description = "Cria um novo pet vinculado a um tutor existente. O tutor deve ser informado pelo campo tutorId."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    })
    public PetResponse addPet(@RequestBody @Valid PetRequest request) {
        return petService.addPet(request);
    }

    @GetMapping
    @Operation(
            summary = "Listar pets",
            description = "Retorna uma lista paginada de pets cadastrados. Permite paginação e ordenação por parâmetros como page, size e sort."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pets listados com sucesso")
    })
    public Page<PetResponse> getAllPets(Pageable pageable) {
        return petService.getAllPets(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar pet por ID",
            description = "Retorna os dados de um pet específico a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    public PetResponse getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar pet",
            description = "Atualiza os dados de um pet existente, incluindo suas informações básicas e o tutor responsável."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Pet ou tutor não encontrado")
    })
    public PetResponse updatePet(@PathVariable Long id, @RequestBody @Valid PetRequest request
    ) {
        return petService.updatePet(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar pet",
            description = "Remove um pet do sistema a partir do ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    public void deletePet(@PathVariable Long id) {
        petService.deletePet(id);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Buscar pet por nome",
            description = "Retorna pets cujo nome contenha o valor informado no parâmetro name."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    })
    public List<PetResponse> searchPetsByName(@RequestParam String name) {
        return petService.searchPetsByName(name);
    }
}