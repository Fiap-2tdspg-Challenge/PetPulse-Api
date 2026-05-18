package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.TutorRequest;
import fiap.com.br.petpulse.dto.TutorResponse;
import fiap.com.br.petpulse.service.TutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutors")
@Slf4j
@Tag(name = "Tutor", description = "Endpoint para gerenciamento de tutores")
public class TutorController {

    @Autowired
    private TutorService service;

    @GetMapping
    @Operation(
            summary = "Listar todos os tutores",
            description = "Retorna uma lista completa de todos os tutores cadastrados no banco de dados."
    )
    public List<TutorResponse> listAll() {
        log.info("Listando todos os tutores");
        return service.getAllTutors();
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar novo tutor",
            description = "Cria um novo registro de tutor. O ID e a data de cadastro são gerados automaticamente."
    )
    public ResponseEntity<TutorResponse> createTutor(@RequestBody @Valid TutorRequest request) {
        log.info("Criando novo tutor: {}", request.name());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addTutor(request));
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Obter tutor por ID",
            description = "Busca os detalhes de um tutor específico através do seu identificador único."
    )
    public ResponseEntity<TutorResponse> getTutorById(@PathVariable Long id) {
        log.info("Obtendo dados do tutor {}", id);

        return ResponseEntity.ok(service.getTutorById(id));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Deletar tutor",
            description = "Remove permanentemente um tutor do sistema através do ID."
    )
    public ResponseEntity<Void> deleteTutor(@PathVariable Long id) {
        log.info("Deletando tutor com id {}", id);

        service.deleteTutor(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Atualizar tutor",
            description = "Atualiza os dados de um tutor existente."
    )
    public ResponseEntity<TutorResponse> updateTutor(
            @PathVariable Long id,
            @RequestBody @Valid TutorRequest request
    ) {
        log.info("Atualizando tutor com id {}", id);

        return ResponseEntity.ok(service.updateTutor(id, request));
    }
}