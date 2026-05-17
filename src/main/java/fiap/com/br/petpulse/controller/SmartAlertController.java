package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.model.SmartAlert;
import fiap.com.br.petpulse.service.SmartAlertService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/smart-alerts")
public class SmartAlertController {

    @Autowired
    private SmartAlertService smartAlertService;

    @PostMapping
    public SmartAlert addSmartAlert(@RequestBody @Valid SmartAlert smartAlert) {
        return smartAlertService.addSmartAlert(smartAlert);
    }

    @GetMapping
    public List<SmartAlert> getAllSmartAlerts() {
        return smartAlertService.getAllSmartAlerts();
    }

    @GetMapping("/{id}")
    public SmartAlert getSmartAlertById(@PathVariable Long id) {
        return smartAlertService.getSmartAlertById(id);
    }

    @PutMapping("/{id}")
    public SmartAlert updateSmartAlert(
            @PathVariable Long id,
            @RequestBody @Valid SmartAlert smartAlert
    ) {
        return smartAlertService.updateSmartAlert(id, smartAlert);
    }

    @DeleteMapping("/{id}")
    public void deleteSmartAlert(@PathVariable Long id) {
        smartAlertService.deleteSmartAlert(id);
    }
}
