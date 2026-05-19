package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.ClinicalHistoryRequest;
import fiap.com.br.petpulse.dto.ClinicalHistoryResponse;
import fiap.com.br.petpulse.model.ClinicalHistory;
import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.repositories.ClinicalHistoryRepository;
import fiap.com.br.petpulse.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "clinicalHistories")
@Service
public class ClinicalHistoryService {

    @Autowired
    private ClinicalHistoryRepository clinicalHistoryRepository;

    @Autowired
    private PetRepository petRepository;

    @CacheEvict
    public ClinicalHistoryResponse addClinicalHistory(ClinicalHistoryRequest request) {

        Pet pet = petRepository.findById(request.petId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pet com id " + request.petId() + " não encontrado"
                )
        );

        ClinicalHistory clinicalHistory = request.toEntity();
        clinicalHistory.setPet(pet);

        return ClinicalHistoryResponse.toResponse(clinicalHistoryRepository.save(clinicalHistory));
    }

    @Cacheable
    public Page<ClinicalHistoryResponse> getAllClinicalHistories(Pageable pageable) {
        return clinicalHistoryRepository.findAll(pageable)
                .map(ClinicalHistoryResponse::toResponse);
    }

    @Cacheable
    public ClinicalHistoryResponse getClinicalHistoryById(Long id) {
        return ClinicalHistoryResponse.toResponse(findClinicalHistoryById(id));
    }

    @CacheEvict
    public void deleteClinicalHistory(Long id) {
        findClinicalHistoryById(id);
        clinicalHistoryRepository.deleteById(id);
    }

    @CacheEvict
    public ClinicalHistoryResponse updateClinicalHistory(Long id, ClinicalHistoryRequest request) {

        ClinicalHistory clinicalHistory = findClinicalHistoryById(id);

        Pet pet = petRepository.findById(request.petId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pet com id " + request.petId() + " não encontrado"
                )
        );

        clinicalHistory.setRecordType(request.recordType());
        clinicalHistory.setDescription(request.description());
        clinicalHistory.setReturnDate(request.returnDate());
        clinicalHistory.setClinicProfessional(request.clinicProfessional());
        clinicalHistory.setObservations(request.observations());
        clinicalHistory.setPet(pet);

        return ClinicalHistoryResponse.toResponse(clinicalHistoryRepository.save(clinicalHistory));
    }


    private ClinicalHistory findClinicalHistoryById(Long id) {
        return clinicalHistoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Histórico clínico com id " + id + " não encontrado"
                )
        );
    }
}
