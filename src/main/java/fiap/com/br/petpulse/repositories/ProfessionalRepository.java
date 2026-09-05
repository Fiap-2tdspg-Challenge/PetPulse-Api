package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepository
        extends JpaRepository<Professional, Long> {
}