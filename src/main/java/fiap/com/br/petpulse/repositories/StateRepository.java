package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, String> {
}
