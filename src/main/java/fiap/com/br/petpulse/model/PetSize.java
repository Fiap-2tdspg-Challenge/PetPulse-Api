package fiap.com.br.petpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_CLY_PORTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PORTE")
    private Long id;

    @Column(name = "DESCRICAO")
    private String description;
}