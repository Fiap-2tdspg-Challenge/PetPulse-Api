package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.TutorRequest;
import fiap.com.br.petpulse.dto.TutorResponse;
import fiap.com.br.petpulse.service.TutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/tutors")
@Slf4j
@Tag(name = "Tutor", description = "Endpoints para gerenciamento dos tutores responsáveis pelos pets")
public class TutorController {

    @Autowired
    private TutorService service;

    @GetMapping
    @Operation(
            summary = "Listar tutores",
            description = "Retorna uma lista paginada de tutores cadastrados no sistema. Permite paginação e ordenação por parâmetros."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutores listados com sucesso")
    })
    public Page<TutorResponse> listAll(Pageable pageable) {
        log.info("Listando todos os tutores com paginação");
        return service.getAllTutors(pageable);
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar novo tutor",
            description = "Cria um novo tutor responsável por um ou mais pets. A data de cadastro é gerada automaticamente pelo sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tutor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição")
    })
    public ResponseEntity<TutorResponse> createTutor(@RequestBody @Valid TutorRequest request) {
        log.info("Criando novo tutor: {}", request.name());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addTutor(request));
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Buscar tutor por ID",
            description = "Retorna os dados de um tutor específico a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    })
    public ResponseEntity<TutorResponse> getTutorById(@PathVariable Long id) {
        log.info("Obtendo dados do tutor {}", id);

        return ResponseEntity.ok(service.getTutorById(id));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Deletar tutor",
            description = "Remove um tutor do sistema a partir do ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tutor removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    })
    public ResponseEntity<Void> deleteTutor(@PathVariable Long id) {
        log.info("Deletando tutor com id {}", id);

        service.deleteTutor(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Atualizar tutor",
            description = "Atualiza os dados de um tutor existente com base no ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    })
    public ResponseEntity<TutorResponse> updateTutor(
            @PathVariable Long id,
            @RequestBody @Valid TutorRequest request
    ) {
        log.info("Atualizando tutor com id {}", id);

        return ResponseEntity.ok(service.updateTutor(id, request));
    }

    @GetMapping("/search")
    @Operation(
            summary = "Buscar tutor por nome",
            description = "Retorna tutores cujo nome contenha o valor informado no parâmetro name."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    })
    public List<TutorResponse> searchTutorsByName(@RequestParam String name) {
        log.info("Buscando tutores pelo nome: {}", name);
        return service.searchTutorsByName(name);
    }
}