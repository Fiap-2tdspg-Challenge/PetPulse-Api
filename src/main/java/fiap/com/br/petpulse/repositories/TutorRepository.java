package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository <Tutor, Long> {
    List<Tutor> findByNameContainingIgnoreCase(String name);

    Optional<Tutor> findByEmailIgnoreCase(String email);
    Optional<Tutor> findByEmail(String email);
}
