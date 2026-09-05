package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
}