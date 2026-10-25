package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByNameContainingIgnoreCase(String name);

    boolean existsByTutor_Id(Long tutorId);

    // Chama a procedure de carga do banco (Sprint 3/4)
    // Se o 06_PACKAGES NÃO foi rodado nesse schema, use procedureName = "PRC_CARGA_PET"
    @Procedure(procedureName = "PKG_CARGA.PRC_CARGA_PET")
    void cargaPet(
            @Param("p_id_usuario")    Long idUsuario,
            @Param("p_nome")          String nome,
            @Param("p_id_especie")    Long idEspecie,
            @Param("p_id_raca")       Long idRaca,
            @Param("p_id_porte")      Long idPorte,
            @Param("p_dt_nascimento") LocalDate dtNascimento,
            @Param("p_peso") BigDecimal peso,
            @Param("p_sexo")          String sexo,
            @Param("p_castrado")      String castrado
    );
    // A procedure não devolve o ID: busca o último pet com esse nome desse tutor
    Optional<Pet> findFirstByTutor_IdAndNameOrderByIdDesc(Long tutorId, String name);

}
