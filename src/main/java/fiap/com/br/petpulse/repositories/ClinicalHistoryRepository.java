package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.ClinicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicalHistoryRepository
        extends JpaRepository<ClinicalHistory, Long> {

    List<ClinicalHistory> findByPetIdOrderByRecordDateDesc(Long petId);
}