package fiap.com.br.petpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_CLY_RACA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RACA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ESPECIE")
    private Species species;

    @Column(name = "NOME_RACA")
    private String name;
}