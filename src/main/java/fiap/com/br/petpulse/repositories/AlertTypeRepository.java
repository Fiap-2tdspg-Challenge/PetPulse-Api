package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.AlertType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertTypeRepository
        extends JpaRepository<AlertType, Long> {
}