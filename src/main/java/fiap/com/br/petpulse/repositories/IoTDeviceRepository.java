package fiap.com.br.petpulse.repositories;

import fiap.com.br.petpulse.model.IoTDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IoTDeviceRepository extends JpaRepository<IoTDevice, Long> {
}
