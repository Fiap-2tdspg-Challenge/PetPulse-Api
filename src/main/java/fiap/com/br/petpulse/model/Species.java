package fiap.com.br.petpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_CLY_ESPECIE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Species {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESPECIE")
    private Long id;

    @Column(name = "NOME_ESPECIE")
    private String name;

    @ManyToOne
    @JoinColumn(name = "ID_ESPECIE")
    private Species species;
}