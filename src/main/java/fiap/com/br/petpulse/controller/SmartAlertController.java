package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.SmartAlertRequest;
import fiap.com.br.petpulse.dto.SmartAlertResponse;
import fiap.com.br.petpulse.service.SmartAlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/smart-alerts")
@Tag(name = "Smart Alert", description = "Endpoints para gerenciamento de alertas inteligentes gerados para os pets")
public class SmartAlertController {

    @Autowired
    private SmartAlertService smartAlertService;

    @PostMapping
    @Operation(
            summary = "Cadastrar alerta inteligente",
            description = "Cria um novo alerta inteligente vinculado a um pet, podendo ser originado pelo sistema, usuário, histórico clínico ou dispositivo IoT."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alerta inteligente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    public SmartAlertResponse addSmartAlert(@RequestBody @Valid SmartAlertRequest request) {
        return smartAlertService.addSmartAlert(request);
    }

    @GetMapping
    @Operation(
            summary = "Listar alertas inteligentes",
            description = "Retorna uma lista paginada de alertas inteligentes cadastrados. Permite paginação e ordenação por parâmetros como page, size e sort."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alertas inteligentes listados com sucesso")
    })
    public Page<SmartAlertResponse> getAllSmartAlerts(Pageable pageable) {
        return smartAlertService.getAllSmartAlerts(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar alerta inteligente por ID",
            description = "Retorna os dados de um alerta inteligente específico a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta inteligente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta inteligente não encontrado")
    })
    public SmartAlertResponse getSmartAlertById(@PathVariable Long id) {
        return smartAlertService.getSmartAlertById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar alerta inteligente",
            description = "Atualiza os dados de um alerta inteligente existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta inteligente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Alerta inteligente ou pet não encontrado")
    })
    public SmartAlertResponse updateSmartAlert(
            @PathVariable Long id,
            @RequestBody @Valid SmartAlertRequest request
    ) {
        return smartAlertService.updateSmartAlert(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar alerta inteligente",
            description = "Remove um alerta inteligente do sistema a partir do ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alerta inteligente removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta inteligente não encontrado")
    })
    public void deleteSmartAlert(@PathVariable Long id) {
        smartAlertService.deleteSmartAlert(id);
    }
}