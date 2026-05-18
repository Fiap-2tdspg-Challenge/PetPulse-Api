package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.ClinicalHistoryRequest;
import fiap.com.br.petpulse.dto.ClinicalHistoryResponse;
import fiap.com.br.petpulse.service.ClinicalHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinical-histories")
public class ClinicalHistoryController {

    @Autowired
    private ClinicalHistoryService clinicalHistoryService;

    @PostMapping
    public ClinicalHistoryResponse addClinicalHistory(
            @RequestBody @Valid ClinicalHistoryRequest request
    ) {
        return clinicalHistoryService.addClinicalHistory(request);
    }

    @GetMapping
    public List<ClinicalHistoryResponse> getAllClinicalHistories() {
        return clinicalHistoryService.getAllClinicalHistories();
    }

    @GetMapping("/{id}")
    public ClinicalHistoryResponse getClinicalHistoryById(@PathVariable Long id) {
        return clinicalHistoryService.getClinicalHistoryById(id);
    }

    @PutMapping("/{id}")
    public ClinicalHistoryResponse updateClinicalHistory(
            @PathVariable Long id,
            @RequestBody @Valid ClinicalHistoryRequest request
    ) {
        return clinicalHistoryService.updateClinicalHistory(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteClinicalHistory(@PathVariable Long id) {
        clinicalHistoryService.deleteClinicalHistory(id);
    }
}