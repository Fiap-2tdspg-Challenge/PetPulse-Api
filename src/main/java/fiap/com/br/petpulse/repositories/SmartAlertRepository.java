package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.SmartAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmartAlertRepository extends JpaRepository<SmartAlert, Long> {
}
