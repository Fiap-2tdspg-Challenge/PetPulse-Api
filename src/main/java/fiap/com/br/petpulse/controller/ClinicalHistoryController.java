package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.model.ClinicalHistory;
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
    public ClinicalHistory addClinicalHistory(@RequestBody @Valid ClinicalHistory clinicalHistory) {
        return clinicalHistoryService.addClinicalHistory(clinicalHistory);
    }

    @GetMapping
    public List<ClinicalHistory> getAllClinicalHistories() {
        return clinicalHistoryService.getAllClinicalHistories();
    }

    @GetMapping("/{id}")
    public ClinicalHistory getClinicalHistoryById(@PathVariable Long id) {
        return clinicalHistoryService.getClinicalHistoryById(id);
    }

    @PutMapping("/{id}")
    public ClinicalHistory updateClinicalHistory(
            @PathVariable Long id,
            @RequestBody @Valid ClinicalHistory clinicalHistory
    ) {
        return clinicalHistoryService.updateClinicalHistory(id, clinicalHistory);
    }

    @DeleteMapping("/{id}")
    public void deleteClinicalHistory(@PathVariable Long id) {
        clinicalHistoryService.deleteClinicalHistory(id);
    }
}
