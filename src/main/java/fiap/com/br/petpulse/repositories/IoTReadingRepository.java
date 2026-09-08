package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.IoTReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IoTReadingRepository
        extends JpaRepository<IoTReading, Long> {

    List<IoTReading> findTop5ByDevicePetIdOrderByReadingDateDesc(Long petId);
}