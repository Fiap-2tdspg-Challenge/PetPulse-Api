package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Long> {
}