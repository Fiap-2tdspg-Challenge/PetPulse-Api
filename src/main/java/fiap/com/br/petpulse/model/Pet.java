package fiap.com.br.petpulse.model;

import fiap.com.br.petpulse.Enums.Sex;
import fiap.com.br.petpulse.Enums.PetSize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "T_CLY_PET")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String species;
    private String breed; // <- raça
    private LocalDate birthDate;
    private Double weight;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    private boolean neutered; // <- castrado
    @Enumerated(EnumType.STRING)
    private PetSize petSize;
    private String knownDiseases;

    @ManyToOne
    private Tutor tutor;
}
