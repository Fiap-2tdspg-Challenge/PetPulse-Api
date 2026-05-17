package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.model.SmartAlert;
import fiap.com.br.petpulse.repositories.SmartAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SmartAlertService {

    @Autowired
    private SmartAlertRepository smartAlertRepository;

    public SmartAlert addSmartAlert(SmartAlert smartAlert){
        return smartAlertRepository.save(smartAlert);
    }

    public List<SmartAlert> getAllSmartAlerts() {
        return smartAlertRepository.findAll();
    }

    public SmartAlert getSmartAlertById(Long id){
        return findSmartAlertById(id);
    }

    public void deleteSmartAlert(Long id) {
        findSmartAlertById(id);
        smartAlertRepository.deleteById(id);
    }

    public SmartAlert updateSmartAlert(Long id, SmartAlert newSmartAlert) {
        findSmartAlertById(id);
        newSmartAlert.setId(id);
        return smartAlertRepository.save(newSmartAlert);
    }

    private SmartAlert findSmartAlertById(Long id) {
        return smartAlertRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Alerta inteligente com id " + id + " não encontrado"
                )
        );
    }
}
