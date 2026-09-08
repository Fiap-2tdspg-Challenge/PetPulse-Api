package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.request.TutorPhoneRequest;
import fiap.com.br.petpulse.dto.response.TutorPhoneResponse;
import fiap.com.br.petpulse.service.TutorPhoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutor-phones")
@PreAuthorize("hasRole('TUTOR')")
@RequiredArgsConstructor
@Tag(name = "Tutor Phone", description = "Endpoints para gerenciamento do telefone dos tutores")
public class TutorPhoneController {

    private final TutorPhoneService tutorPhoneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar telefone",
            description = "Cria um novo telefone para um tutor."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Telefone cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    })
    public TutorPhoneResponse addTutorPhone(@RequestBody @Valid TutorPhoneRequest request) {
        return tutorPhoneService.addTutorPhone(request);
    }

    @GetMapping
    @Operation(
            summary = "Listar telefones",
            description = "Retorna uma lista paginada de telefones cadastrados. Permite paginação e ordenação por parâmetros como page, size e sort."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Telefones listados com sucesso")
    })
    public Page<TutorPhoneResponse> getAllTutorPhones(Pageable pageable) {
        return tutorPhoneService.getAllTutorPhones(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar telefone por ID",
            description = "Retorna os dados de um telefone específico a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Telefone encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    })
    public TutorPhoneResponse getTutorPhoneById(@PathVariable Long id) {
        return tutorPhoneService.getTutorPhoneById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar telefone",
            description = "Atualiza os dados de um telefone existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    })
    public TutorPhoneResponse updateTutorPhone(
            @PathVariable Long id,
            @RequestBody @Valid TutorPhoneRequest request
    ) {
        return tutorPhoneService.updateTutorPhone(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Deletar telefone",
            description = "Remove um telefone a partir do ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Telefone removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Telefone não encontrado")
    })
    public void deleteTutorPhone(@PathVariable Long id) {
        tutorPhoneService.deleteTutorPhone(id);
    }
}
