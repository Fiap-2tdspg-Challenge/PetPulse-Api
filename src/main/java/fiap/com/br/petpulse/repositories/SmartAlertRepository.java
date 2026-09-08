package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.SmartAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmartAlertRepository
        extends JpaRepository<SmartAlert, Long> {

    List<SmartAlert> findByPetIdOrderByGeneratedAtDesc(Long petId);
}