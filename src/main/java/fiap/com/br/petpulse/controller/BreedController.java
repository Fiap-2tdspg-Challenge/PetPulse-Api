package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.request.BreedRequest;
import fiap.com.br.petpulse.dto.response.BreedResponse;
import fiap.com.br.petpulse.service.BreedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/breeds")
@RequiredArgsConstructor
@Tag(name = "Breed", description = "Endpoints para a tabela de referência de raças de pets")
public class BreedController {

    private final BreedService breedService;

    @PostMapping
    @Operation(
            summary = "Buscar ou cadastrar raça",
            description = "Retorna a raça existente com o nome informado para a espécie indicada " +
                    "(ignorando maiúsculas/minúsculas) ou cria uma nova, caso ainda não exista. Usado " +
                    "pelo app para permitir que o tutor digite livremente a raça do pet."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Raça encontrada ou cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Espécie não encontrada")
    })
    public BreedResponse findOrCreate(@RequestBody @Valid BreedRequest request) {
        return breedService.findOrCreate(request);
    }

    @GetMapping
    @Operation(
            summary = "Listar raças por espécie",
            description = "Retorna todas as raças cadastradas para a espécie informada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Raças listadas com sucesso")
    })
    public List<BreedResponse> getBreedsBySpecies(@RequestParam Long speciesId) {
        return breedService.getBreedsBySpecies(speciesId);
    }
}
