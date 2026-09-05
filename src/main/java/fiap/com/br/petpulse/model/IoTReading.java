package fiap.com.br.petpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_CLY_LEITURA_IOT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IoTReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LEITURA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_DISPOSITIVO", nullable = false)
    private IoTDevice device;

    @Column(name = "DT_LEITURA", nullable = false)
    private LocalDateTime readingDate;

    @Column(name = "FREQUENCIA_CARDIACA")
    private Integer heartRate;

    @Column(name = "NIVEL_ATIVIDADE", precision = 5, scale = 2)
    private BigDecimal activityLevel;

    @Column(name = "PRESSAO", precision = 6, scale = 2)
    private BigDecimal pressure;

    @PrePersist
    public void prePersist() {
        if (readingDate == null) {
            readingDate = LocalDateTime.now();
        }
    }
}