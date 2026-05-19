package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.ClinicalHistoryRequest;
import fiap.com.br.petpulse.dto.ClinicalHistoryResponse;
import fiap.com.br.petpulse.service.ClinicalHistoryService;
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
@RequestMapping("/clinical-histories")
@Tag(name = "Clinical History", description = "Endpoints para gerenciamento do histórico clínico dos pets")
public class ClinicalHistoryController {

    @Autowired
    private ClinicalHistoryService clinicalHistoryService;

    @PostMapping
    @Operation(
            summary = "Cadastrar histórico clínico",
            description = "Cria um novo registro no histórico clínico de um pet, como vacina, consulta, doença, medicamento, observação ou exame."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Histórico clínico cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    public ClinicalHistoryResponse addClinicalHistory(
            @RequestBody @Valid ClinicalHistoryRequest request
    ) {
        return clinicalHistoryService.addClinicalHistory(request);
    }

    @GetMapping
    @Operation(
            summary = "Listar históricos clínicos",
            description = "Retorna uma lista paginada de históricos clínicos cadastrados. Permite paginação e ordenação por parâmetros como page, size e sort."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Históricos clínicos listados com sucesso")
    })
    public Page<ClinicalHistoryResponse> getAllClinicalHistories(Pageable pageable) {
        return clinicalHistoryService.getAllClinicalHistories(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar histórico clínico por ID",
            description = "Retorna os dados de um registro específico do histórico clínico a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico clínico encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Histórico clínico não encontrado")
    })
    public ClinicalHistoryResponse getClinicalHistoryById(@PathVariable Long id) {
        return clinicalHistoryService.getClinicalHistoryById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar histórico clínico",
            description = "Atualiza os dados de um registro existente do histórico clínico de um pet."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico clínico atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Histórico clínico ou pet não encontrado")
    })
    public ClinicalHistoryResponse updateClinicalHistory(
            @PathVariable Long id,
            @RequestBody @Valid ClinicalHistoryRequest request
    ) {
        return clinicalHistoryService.updateClinicalHistory(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar histórico clínico",
            description = "Remove um registro do histórico clínico a partir do ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Histórico clínico removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Histórico clínico não encontrado")
    })
    public void deleteClinicalHistory(@PathVariable Long id) {
        clinicalHistoryService.deleteClinicalHistory(id);
    }
}