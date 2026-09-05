package fiap.com.br.petpulse.model;

import fiap.com.br.petpulse.config.BooleanToSNConverter;
import fiap.com.br.petpulse.enums.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_CLY_PET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PET")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "ID_ESPECIE")
    private Species species;

    @ManyToOne
    @JoinColumn(name = "ID_RACA")
    private Breed breed;

    @ManyToOne
    @JoinColumn(name = "ID_PORTE")
    private PetSize petSize;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DT_NASCIMENTO")
    private LocalDate birthDate;

    @Column(name = "PESO", precision = 6, scale = 2)
    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEXO", nullable = false, columnDefinition = "CHAR(1)")
    private Sex sex;

    @Convert(converter = BooleanToSNConverter.class)
    @Column(name = "CASTRADO", nullable = false, columnDefinition = "CHAR(1)")
    private Boolean neutered;

    @Column(name = "DT_CADASTRO")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}