package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.AlertType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertTypeRepository
        extends JpaRepository<AlertType, Long> {
    Optional<AlertType> findByDescription(String description);
}