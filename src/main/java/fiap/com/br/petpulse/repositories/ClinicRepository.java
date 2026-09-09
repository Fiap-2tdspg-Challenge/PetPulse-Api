package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
}
