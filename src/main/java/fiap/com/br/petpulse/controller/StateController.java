package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.request.StateRequest;
import fiap.com.br.petpulse.dto.response.StateResponse;
import fiap.com.br.petpulse.service.StateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
@Tag(name = "State", description = "Endpoints para a tabela de referência de estados (UF)")
public class StateController {

    private final StateService stateService;

    @PostMapping
    @Operation(
            summary = "Buscar ou cadastrar estado",
            description = "Retorna o estado (UF) existente com o código informado ou cria um novo, caso ainda não exista."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado ou cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição")
    })
    public StateResponse findOrCreate(@RequestBody @Valid StateRequest request) {
        return stateService.findOrCreate(request);
    }

    @GetMapping
    @Operation(
            summary = "Listar estados",
            description = "Retorna todos os estados cadastrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estados listados com sucesso")
    })
    public List<StateResponse> getAllStates() {
        return stateService.getAllStates();
    }
}
