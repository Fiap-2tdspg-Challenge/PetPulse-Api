package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByNameIgnoreCaseAndState_Code(String name, String stateCode);

    List<City> findByState_CodeIgnoreCase(String stateCode);

    // COD_CIDADE não é auto-incremento no banco, então o próximo id é
    // calculado aqui na criação.
    @Query("SELECT COALESCE(MAX(c.id), 0) FROM City c")
    Long findMaxId();
}
