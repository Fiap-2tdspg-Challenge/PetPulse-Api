package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    Optional<Professional> findByEmail(String email);
}