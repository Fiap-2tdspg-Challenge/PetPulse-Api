package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.SmartAlertRequest;
import fiap.com.br.petpulse.dto.SmartAlertResponse;
import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.model.SmartAlert;
import fiap.com.br.petpulse.repositories.PetRepository;
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

    @Autowired
    private PetRepository petRepository;

    public SmartAlertResponse addSmartAlert(SmartAlertRequest request) {

        Pet pet = petRepository.findById(request.petId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pet com id " + request.petId() + " não encontrado"
                )
        );

        SmartAlert smartAlert = request.toEntity();
        smartAlert.setPet(pet);

        return SmartAlertResponse.toResponse(smartAlertRepository.save(smartAlert));
    }

    public List<SmartAlertResponse> getAllSmartAlerts() {
        return smartAlertRepository.findAll()
                .stream()
                .map(SmartAlertResponse::toResponse)
                .toList();
    }

    public SmartAlertResponse getSmartAlertById(Long id) {
        return SmartAlertResponse.toResponse(findSmartAlertById(id));
    }

    public void deleteSmartAlert(Long id) {
        findSmartAlertById(id);
        smartAlertRepository.deleteById(id);
    }

    public SmartAlertResponse updateSmartAlert(Long id, SmartAlertRequest request) {

        SmartAlert smartAlert = findSmartAlertById(id);

        Pet pet = petRepository.findById(request.petId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pet com id " + request.petId() + " não encontrado"
                )
        );

        smartAlert.setAlertType(request.alertType());
        smartAlert.setRiskLevel(request.riskLevel());
        smartAlert.setAlertOrigin(request.alertOrigin());
        smartAlert.setMessage(request.message());
        smartAlert.setRecommendation(request.recommendation());
        smartAlert.setPet(pet);

        return SmartAlertResponse.toResponse(smartAlertRepository.save(smartAlert));
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