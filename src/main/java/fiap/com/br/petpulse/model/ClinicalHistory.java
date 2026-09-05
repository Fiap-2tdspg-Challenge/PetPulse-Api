package fiap.com.br.petpulse.model;

import fiap.com.br.petpulse.enums.RecordType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "T_CLY_HISTORICO_CLINICO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClinicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HISTORICO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PET", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "ID_PROFISSIONAL")
    private Professional professional;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_REGISTRO", length = 50)
    private RecordType recordType;

    @Column(name = "DESCRICAO", length = 500)
    private String description;

    @Column(name = "DT_REGISTRO", nullable = false)
    private LocalDate recordDate;

    @Column(name = "DT_RETORNO")
    private LocalDate returnDate;

    @Column(name = "OBSERVACOES", length = 1000)
    private String observations;

    @PrePersist
    public void prePersist() {
        if (recordDate == null) {
            recordDate = LocalDate.now();
        }
    }
}