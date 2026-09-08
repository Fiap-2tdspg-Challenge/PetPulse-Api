package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.response.PetSizeResponse;
import fiap.com.br.petpulse.service.PetSizeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pet-sizes")
@RequiredArgsConstructor
@Tag(name = "Pet Size", description = "Endpoints para a tabela de referência de portes de pets")
public class PetSizeController {

    private final PetSizeService petSizeService;

    @GetMapping
    @Operation(
            summary = "Listar portes",
            description = "Retorna todos os portes de pet cadastrados (ex: Pequeno, Médio, Grande)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Portes listados com sucesso")
    })
    public List<PetSizeResponse> getAllPetSizes() {
        return petSizeService.getAllPetSizes();
    }
}
