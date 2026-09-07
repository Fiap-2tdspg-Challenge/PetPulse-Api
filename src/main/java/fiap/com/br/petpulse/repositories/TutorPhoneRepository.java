package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.TutorPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TutorPhoneRepository extends JpaRepository<TutorPhone, Long> {

    List<TutorPhone> findByTutor_Id(Long tutorId);

    // ID_TELEFONE não é auto-incremento no banco, então o próximo id é
    // calculado aqui na criação.
    @Query("SELECT COALESCE(MAX(t.id), 0) FROM TutorPhone t")
    Long findMaxId();
}
