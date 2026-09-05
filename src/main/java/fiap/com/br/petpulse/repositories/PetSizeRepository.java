package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.PetSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetSizeRepository extends JpaRepository<PetSize, Long> {
}