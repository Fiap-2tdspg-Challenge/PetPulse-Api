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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "smartAlerts")
@Service
public class SmartAlertService {

    @Autowired
    private SmartAlertRepository smartAlertRepository;

    @Autowired
    private PetRepository petRepository;

    @CacheEvict
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

    @Cacheable
    public Page<SmartAlertResponse> getAllSmartAlerts(Pageable pageable) {
        return smartAlertRepository.findAll(pageable)
                .map(SmartAlertResponse::toResponse);
    }

    @Cacheable
    public SmartAlertResponse getSmartAlertById(Long id) {
        return SmartAlertResponse.toResponse(findSmartAlertById(id));
    }

    @CacheEvict
    public void deleteSmartAlert(Long id) {
        findSmartAlertById(id);
        smartAlertRepository.deleteById(id);
    }

    @CacheEvict
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