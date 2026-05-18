package fiap.com.br.petpulse.model;

import fiap.com.br.petpulse.enums.DeviceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "T_CLY_DISPOSITIVO_IOT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IoTDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate linkedAt;
    private Integer collectionIntervalMinutes;
    private Integer heartRate;
    private Double activityLevel;
    private Double pressure;
    private LocalDate lastReadingDate;
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;
    @PrePersist
    public void prePersist() {
        this.linkedAt = LocalDate.now();

        if (this.status == null) {
            this.status = DeviceStatus.ATIVO;
        }
    }

    @OneToOne
    private Pet pet;
}
