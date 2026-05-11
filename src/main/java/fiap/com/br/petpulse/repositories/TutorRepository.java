package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends JpaRepository <Tutor, Long> {
}
