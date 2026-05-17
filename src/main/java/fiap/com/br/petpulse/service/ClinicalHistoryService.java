package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.model.ClinicalHistory;
import fiap.com.br.petpulse.repositories.ClinicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClinicalHistoryService {
    @Autowired
    private ClinicalHistoryRepository clinicalHistoryRepository;

    public ClinicalHistory addClinicalHistory(ClinicalHistory clinicalHistory){
        return clinicalHistoryRepository.save(clinicalHistory);
    }

    public List<ClinicalHistory> getAllClinicalHistories() {
        return clinicalHistoryRepository.findAll();
    }

    public ClinicalHistory getClinicalHistoryById(Long id){
        return findClinicalHistoryById(id);
    }

    public void deleteClinicalHistory(Long id) {
        findClinicalHistoryById(id);
        clinicalHistoryRepository.deleteById(id);
    }

    public ClinicalHistory updateClinicalHistory(Long id, ClinicalHistory newClinicalHistory) {
        findClinicalHistoryById(id);
        newClinicalHistory.setId(id);
        return clinicalHistoryRepository.save(newClinicalHistory);
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
