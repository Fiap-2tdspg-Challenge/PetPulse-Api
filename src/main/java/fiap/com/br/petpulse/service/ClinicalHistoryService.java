package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.assembler.ClinicalHistoryAssembler;
import fiap.com.br.petpulse.dto.request.ClinicalHistoryRequest;
import fiap.com.br.petpulse.dto.response.ClinicalHistoryResponse;
import fiap.com.br.petpulse.model.ClinicalHistory;
import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.model.Professional;
import fiap.com.br.petpulse.repositories.ClinicalHistoryRepository;
import fiap.com.br.petpulse.repositories.PetRepository;
import fiap.com.br.petpulse.repositories.ProfessionalRepository;
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
@CacheConfig(cacheNames = "clinicalHistories")
public class ClinicalHistoryService {

    private final ClinicalHistoryRepository clinicalHistoryRepository;
    private final PetRepository petRepository;
    private final ProfessionalRepository professionalRepository;
    private final ClinicalHistoryAssembler clinicalHistoryAssembler;


    @CacheEvict(allEntries = true)
    public ClinicalHistoryResponse addClinicalHistory(
            ClinicalHistoryRequest request
    ) {

        Pet pet = findPetById(request.petId());

        Professional professional =
                findProfessionalById(request.professionalId());

        ClinicalHistory history =
                clinicalHistoryAssembler.toEntity(
                        request,
                        pet,
                        professional
                );

        return clinicalHistoryAssembler.toResponse(
                clinicalHistoryRepository.save(history)
        );
    }


    @Cacheable
    public Page<ClinicalHistoryResponse> getAllClinicalHistories(
            Pageable pageable
    ) {
        return clinicalHistoryRepository.findAll(pageable)
                .map(clinicalHistoryAssembler::toResponse);
    }


    @Cacheable
    public ClinicalHistoryResponse getClinicalHistoryById(Long id) {
        return clinicalHistoryAssembler.toResponse(
                findClinicalHistoryById(id)
        );
    }


    @CacheEvict(allEntries = true)
    public ClinicalHistoryResponse updateClinicalHistory(
            Long id,
            ClinicalHistoryRequest request
    ) {

        ClinicalHistory history =
                findClinicalHistoryById(id);

        Pet pet = findPetById(request.petId());

        Professional professional =
                findProfessionalById(request.professionalId());

        clinicalHistoryAssembler.updateEntity(
                history,
                request,
                pet,
                professional
        );

        return clinicalHistoryAssembler.toResponse(
                clinicalHistoryRepository.save(history)
        );
    }


    @CacheEvict(allEntries = true)
    public void deleteClinicalHistory(Long id) {

        ClinicalHistory history =
                findClinicalHistoryById(id);

        clinicalHistoryRepository.delete(history);
    }


    private ClinicalHistory findClinicalHistoryById(Long id) {
        return clinicalHistoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Histórico clínico com id "
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


    private Professional findProfessionalById(Long id) {
        return professionalRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Profissional com id "
                                        + id
                                        + " não encontrado"
                        )
                );
    }
}