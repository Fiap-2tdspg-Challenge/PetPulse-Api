package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BreedRepository extends JpaRepository<Breed, Long> {

    Optional<Breed> findBySpecies_IdAndNameIgnoreCase(Long speciesId, String name);

    List<Breed> findBySpecies_Id(Long speciesId);
}