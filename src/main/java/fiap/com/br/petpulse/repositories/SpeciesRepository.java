package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpeciesRepository extends JpaRepository<Species, Long> {

    Optional<Species> findByNameIgnoreCase(String name);
}