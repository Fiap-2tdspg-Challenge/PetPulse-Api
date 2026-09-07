package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.request.TutorAddressRequest;
import fiap.com.br.petpulse.dto.response.TutorAddressResponse;
import fiap.com.br.petpulse.service.TutorAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutor-addresses")
@RequiredArgsConstructor
@Tag(name = "Tutor Address", description = "Endpoints para gerenciamento do endereço dos tutores")
public class TutorAddressController {

    private final TutorAddressService tutorAddressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar endereço",
            description = "Cria um novo endereço para um tutor."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Tutor, tipo de endereço ou cidade não encontrado")
    })
    public TutorAddressResponse addTutorAddress(@RequestBody @Valid TutorAddressRequest request) {
        return tutorAddressService.addTutorAddress(request);
    }

    @GetMapping
    @Operation(
            summary = "Listar endereços",
            description = "Retorna uma lista paginada de endereços cadastrados. Permite paginação e ordenação por parâmetros como page, size e sort."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereços listados com sucesso")
    })
    public Page<TutorAddressResponse> getAllTutorAddresses(Pageable pageable) {
        return tutorAddressService.getAllTutorAddresses(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar endereço por ID",
            description = "Retorna os dados de um endereço específico a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public TutorAddressResponse getTutorAddressById(@PathVariable Long id) {
        return tutorAddressService.getTutorAddressById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar endereço",
            description = "Atualiza os dados de um endereço existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Endereço, tipo de endereço ou cidade não encontrado")
    })
    public TutorAddressResponse updateTutorAddress(
            @PathVariable Long id,
            @RequestBody @Valid TutorAddressRequest request
    ) {
        return tutorAddressService.updateTutorAddress(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Deletar endereço",
            description = "Remove um endereço a partir do ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public void deleteTutorAddress(@PathVariable Long id) {
        tutorAddressService.deleteTutorAddress(id);
    }
}
