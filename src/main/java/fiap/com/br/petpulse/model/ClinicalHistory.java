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
    private Long id;

    @Enumerated(EnumType.STRING)
    private RecordType recordType;

    private String description;

    private LocalDate recordDate;

    private LocalDate returnDate;

    private String clinicProfessional;

    private String observations;

    @PrePersist
    public void prePersist() {
        this.recordDate = LocalDate.now();
    }

    @ManyToOne
    private Pet pet;
}
