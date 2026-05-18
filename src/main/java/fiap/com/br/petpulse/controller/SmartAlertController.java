package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.SmartAlertRequest;
import fiap.com.br.petpulse.dto.SmartAlertResponse;
import fiap.com.br.petpulse.service.SmartAlertService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/smart-alerts")
public class SmartAlertController {

    @Autowired
    private SmartAlertService smartAlertService;

    @PostMapping
    public SmartAlertResponse addSmartAlert(@RequestBody @Valid SmartAlertRequest request) {
        return smartAlertService.addSmartAlert(request);
    }

    @GetMapping
    public Page<SmartAlertResponse> getAllSmartAlerts(Pageable pageable) {
        return smartAlertService.getAllSmartAlerts(pageable);
    }

    @GetMapping("/{id}")
    public SmartAlertResponse getSmartAlertById(@PathVariable Long id) {
        return smartAlertService.getSmartAlertById(id);
    }

    @PutMapping("/{id}")
    public SmartAlertResponse updateSmartAlert(
            @PathVariable Long id,
            @RequestBody @Valid SmartAlertRequest request
    ) {
        return smartAlertService.updateSmartAlert(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteSmartAlert(@PathVariable Long id) {
        smartAlertService.deleteSmartAlert(id);
    }
}