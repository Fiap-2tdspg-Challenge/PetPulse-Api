package fiap.com.br.petpulse.model;

import fiap.com.br.petpulse.enums.AlertOrigin;
import fiap.com.br.petpulse.enums.AlertRiskLevel;
import fiap.com.br.petpulse.enums.AlertStatus;
import fiap.com.br.petpulse.enums.AlertType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "T_CLY_ALERTA_INTELIGENTE")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SmartAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    @Enumerated(EnumType.STRING)
    private AlertRiskLevel riskLevel;

    @Enumerated(EnumType.STRING)
    private AlertOrigin alertOrigin;

    private String message;

    private String recommendation;

    private LocalDate generatedAt;

    @Enumerated(EnumType.STRING)
    private AlertStatus status;

    @PrePersist
    public void prePersist() {

        this.generatedAt = LocalDate.now();

        if (this.status == null) {
            this.status = AlertStatus.ABERTO;
        }
    }

    @ManyToOne
    private Pet pet;
}
