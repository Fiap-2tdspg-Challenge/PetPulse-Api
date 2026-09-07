package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.TutorAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TutorAddressRepository extends JpaRepository<TutorAddress, Long> {

    // SEQ_ENDERECO_USUARIO não é auto-incremento no banco, então o próximo
    // id é calculado aqui na criação.
    @Query("SELECT COALESCE(MAX(t.id), 0) FROM TutorAddress t")
    Long findMaxId();
}
