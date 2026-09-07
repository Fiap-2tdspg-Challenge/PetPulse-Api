package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.request.CityRequest;
import fiap.com.br.petpulse.dto.response.CityResponse;
import fiap.com.br.petpulse.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
@Tag(name = "City", description = "Endpoints para a tabela de referência de cidades")
public class CityController {

    private final CityService cityService;

    @PostMapping
    @Operation(
            summary = "Buscar ou cadastrar cidade",
            description = "Retorna a cidade existente com o nome informado no estado indicado (ignorando " +
                    "maiúsculas/minúsculas) ou cria uma nova, caso ainda não exista."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade encontrada ou cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado")
    })
    public CityResponse findOrCreate(@RequestBody @Valid CityRequest request) {
        return cityService.findOrCreate(request);
    }

    @GetMapping
    @Operation(
            summary = "Listar cidades por estado",
            description = "Retorna todas as cidades cadastradas para o estado informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidades listadas com sucesso")
    })
    public List<CityResponse> getCitiesByState(@RequestParam String stateCode) {
        return cityService.getCitiesByState(stateCode);
    }
}
