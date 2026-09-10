package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.request.ProfessionalRequest;
import fiap.com.br.petpulse.dto.response.ProfessionalResponse;
import fiap.com.br.petpulse.service.ProfessionalService;
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

import java.util.List;

@RestController
@RequestMapping("/professionals")
@RequiredArgsConstructor
@Tag(name = "Professional", description = "Endpoints para gerenciamento dos profissionais (veterinários)")
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @PostMapping
    @PreAuthorize("hasRole('PROFESSIONAL')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar novo profissional",
            description = "Cria um novo profissional (veterinário) vinculado a uma clínica."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profissional cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Clínica não encontrada")
    })
    public ProfessionalResponse addProfessional(@RequestBody @Valid ProfessionalRequest request) {
        return professionalService.addProfessional(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TUTOR', 'PROFESSIONAL')")
    @Operation(
            summary = "Listar profissionais",
            description = "Retorna uma lista paginada de profissionais cadastrados. Permite paginação e ordenação por parâmetros."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissionais listados com sucesso")
    })
    public Page<ProfessionalResponse> getAllProfessionals(Pageable pageable) {
        return professionalService.getAllProfessionals(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TUTOR', 'PROFESSIONAL')")
    @Operation(
            summary = "Buscar profissional por ID",
            description = "Retorna os dados de um profissional específico a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    public ProfessionalResponse getProfessionalById(@PathVariable Long id) {
        return professionalService.getProfessionalById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    @Operation(
            summary = "Atualizar profissional",
            description = "Atualiza os dados de um profissional existente com base no ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Profissional ou clínica não encontrado")
    })
    public ProfessionalResponse updateProfessional(
            @PathVariable Long id,
            @RequestBody @Valid ProfessionalRequest request
    ) {
        return professionalService.updateProfessional(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Deletar profissional",
            description = "Remove um profissional a partir do ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profissional removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    public void deleteProfessional(@PathVariable Long id) {
        professionalService.deleteProfessional(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('TUTOR', 'PROFESSIONAL')")
    @Operation(
            summary = "Buscar profissional por nome",
            description = "Retorna profissionais cujo nome contenha o valor informado no parâmetro name."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    })
    public List<ProfessionalResponse> searchProfessionalsByName(@RequestParam String name) {
        return professionalService.searchProfessionalsByName(name);
    }
}
