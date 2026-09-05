package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.IoTReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IoTReadingRepository
        extends JpaRepository<IoTReading, Long> {
}