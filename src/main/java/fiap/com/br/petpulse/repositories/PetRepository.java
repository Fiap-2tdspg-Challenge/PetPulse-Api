package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByNameContainingIgnoreCase(String name);
}
