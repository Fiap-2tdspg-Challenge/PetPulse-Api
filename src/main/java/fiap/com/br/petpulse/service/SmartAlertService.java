package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.assembler.SmartAlertAssembler;
import fiap.com.br.petpulse.dto.request.SmartAlertRequest;
import fiap.com.br.petpulse.dto.response.SmartAlertResponse;
import fiap.com.br.petpulse.model.AlertType;
import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.model.SmartAlert;
import fiap.com.br.petpulse.repositories.AlertTypeRepository;
import fiap.com.br.petpulse.repositories.PetRepository;
import fiap.com.br.petpulse.repositories.SmartAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "smartAlerts")
public class SmartAlertService {

    private final SmartAlertRepository smartAlertRepository;
    private final PetRepository petRepository;
    private final AlertTypeRepository alertTypeRepository;
    private final SmartAlertAssembler smartAlertAssembler;


    @CacheEvict(allEntries = true)
    public SmartAlertResponse addSmartAlert(
            SmartAlertRequest request
    ) {

        Pet pet = findPetById(request.petId());

        AlertType alertType =
                findAlertTypeById(request.alertTypeId());

        SmartAlert alert =
                smartAlertAssembler.toEntity(
                        request,
                        pet,
                        alertType
                );

        return smartAlertAssembler.toResponse(
                smartAlertRepository.save(alert)
        );
    }


    @Cacheable
    public Page<SmartAlertResponse> getAllSmartAlerts(
            Pageable pageable
    ) {
        return smartAlertRepository.findAll(pageable)
                .map(smartAlertAssembler::toResponse);
    }


    @Cacheable
    public SmartAlertResponse getSmartAlertById(Long id) {
        return smartAlertAssembler.toResponse(
                findSmartAlertById(id)
        );
    }


    @CacheEvict(allEntries = true)
    public SmartAlertResponse updateSmartAlert(
            Long id,
            SmartAlertRequest request
    ) {

        SmartAlert alert = findSmartAlertById(id);

        Pet pet = findPetById(request.petId());

        AlertType alertType =
                findAlertTypeById(request.alertTypeId());

        smartAlertAssembler.updateEntity(
                alert,
                request,
                pet,
                alertType
        );

        return smartAlertAssembler.toResponse(
                smartAlertRepository.save(alert)
        );
    }


    @CacheEvict(allEntries = true)
    public void deleteSmartAlert(Long id) {

        SmartAlert alert = findSmartAlertById(id);

        smartAlertRepository.delete(alert);
    }


    private SmartAlert findSmartAlertById(Long id) {
        return smartAlertRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Alerta com id "
                                        + id
                                        + " não encontrado"
                        )
                );
    }


    private Pet findPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Pet com id "
                                        + id
                                        + " não encontrado"
                        )
                );
    }


    private AlertType findAlertTypeById(Long id) {
        return alertTypeRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Tipo de alerta com id "
                                        + id
                                        + " não encontrado"
                        )
                );
    }
}