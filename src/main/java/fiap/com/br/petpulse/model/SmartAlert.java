package fiap.com.br.petpulse.model;

import fiap.com.br.petpulse.enums.AlertOrigin;
import fiap.com.br.petpulse.enums.AlertRiskLevel;
import fiap.com.br.petpulse.enums.AlertStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_CLY_ALERTA_INTELIGENTE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmartAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALERTA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PET", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_ALERTA", nullable = false)
    private AlertType alertType;

    @Enumerated(EnumType.STRING)
    @Column(name = "NIVEL_RISCO", length = 20)
    private AlertRiskLevel riskLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORIGEM_ALERTA", length = 50)
    private AlertOrigin origin;

    @Column(name = "MENSAGEM", length = 500)
    private String message;

    @Column(name = "RECOMENDACAO", length = 1000)
    private String recommendation;

    @Column(name = "DT_GERACAO", nullable = false)
    private LocalDateTime generatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private AlertStatus status;

    @PrePersist
    public void prePersist() {
        if (generatedAt == null) {
            generatedAt = LocalDateTime.now();
        }

        if (status == null) {
            status = AlertStatus.ABERTO;
        }
    }
}