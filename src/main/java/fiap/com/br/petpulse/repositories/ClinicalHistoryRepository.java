package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.ClinicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicalHistoryRepository extends JpaRepository<ClinicalHistory, Long> {
}
