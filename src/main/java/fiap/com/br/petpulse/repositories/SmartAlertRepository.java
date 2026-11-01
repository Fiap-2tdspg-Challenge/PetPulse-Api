package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.enums.AlertOrigin;
import fiap.com.br.petpulse.model.SmartAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SmartAlertRepository
        extends JpaRepository<SmartAlert, Long> {

    List<SmartAlert> findByPetIdOrderByGeneratedAtDesc(Long petId);

    Optional<SmartAlert> findFirstByPetIdAndOriginOrderByGeneratedAtDesc(Long petId, AlertOrigin origin);
}