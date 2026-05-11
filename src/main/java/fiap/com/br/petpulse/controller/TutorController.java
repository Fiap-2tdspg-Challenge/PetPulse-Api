package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.model.Tutor;
import fiap.com.br.petpulse.service.TutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@Slf4j
@Tag(name = "User", description = "Endpoint para gerenciamento de usuários")
public class TutorController {

    @Autowired
    private TutorService service;

    @GetMapping
    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista completa de todos os usuários cadastrados no banco de dados."
    )
    public List<Tutor> listAll() {
        log.info("Listando todos os usuários");
        return service.getAllUsers();
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar novo usuário",
            description = "Cria um novo registro de usuário. O ID é gerado automaticamente."
    )
    public ResponseEntity<Tutor> createUser(@RequestBody Tutor tutor) {
        log.info("Criando novo usuário: {}", tutor.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addUser(tutor));
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Obter usuário por ID",
            description = "Busca os detalhes de um usuário específico através do seu identificador único."
    )
    public ResponseEntity<Tutor> getUserById(@PathVariable Long id) {
        log.info("Obtendo dados do usuário {}", id);
        return ResponseEntity.ok(service.getUserById(id));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Deletar usuário",
            description = "Remove permanentemente um usuário do sistema através do ID."
    )
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deletando usuário com id {}", id);
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente. Substitui todas as informações do registro pelo corpo enviado."
    )
    public ResponseEntity<Tutor> updateUser(@PathVariable Long id, @RequestBody Tutor tutor) {
        log.info("Atualizando usuário com id {} com os dados {}", id, tutor);
        return ResponseEntity.ok(service.updateUser(id, tutor));
    }
}
