package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.request.SpeciesRequest;
import fiap.com.br.petpulse.dto.response.SpeciesResponse;
import fiap.com.br.petpulse.service.SpeciesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/species")
@RequiredArgsConstructor
@Tag(name = "Species", description = "Endpoints para a tabela de referência de espécies de pets")
public class SpeciesController {

    private final SpeciesService speciesService;

    @PostMapping
    @Operation(
            summary = "Buscar ou cadastrar espécie",
            description = "Retorna a espécie existente com o nome informado (ignorando maiúsculas/minúsculas) " +
                    "ou cria uma nova, caso ainda não exista. Usado pelo app para permitir que o tutor " +
                    "digite livremente a espécie do pet."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Espécie encontrada ou cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição")
    })
    public SpeciesResponse findOrCreate(@RequestBody @Valid SpeciesRequest request) {
        return speciesService.findOrCreate(request);
    }

    @GetMapping
    @Operation(
            summary = "Listar espécies",
            description = "Retorna todas as espécies já cadastradas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Espécies listadas com sucesso")
    })
    public List<SpeciesResponse> getAllSpecies() {
        return speciesService.getAllSpecies();
    }
}
