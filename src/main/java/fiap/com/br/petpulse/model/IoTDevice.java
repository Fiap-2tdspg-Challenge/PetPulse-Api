package fiap.com.br.petpulse.model;

import fiap.com.br.petpulse.enums.DeviceStatus;
import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_CLY_DISPOSITIVO_IOT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IoTDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DISPOSITIVO")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_PET", nullable = false, unique = true)
    private Pet pet;

    @Column(name = "DT_ATIVACAO", nullable = false)
    private LocalDateTime linkedAt;

    @Column(name = "INTERVALO_COLETA_MIN")
    private Integer collectionIntervalMinutes;

    @Column(name = "FREQUENCIA_CARDIACA")
    private Integer heartRate;

    @Column(name = "NIVEL_ATIVIDADE", precision = 5, scale = 2)
    private BigDecimal activityLevel;

    @Column(name = "PRESSAO", precision = 6, scale = 2)
    private BigDecimal pressure;

    @Column(name = "DT_ULTIMA_LEITURA")
    private LocalDateTime lastReadingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private DeviceStatus status;

    @PrePersist
    public void prePersist() {
        if (linkedAt == null) {
            linkedAt = LocalDateTime.now();
        }

        if (status == null) {
            status = DeviceStatus.ATIVO;
        }
    }
}