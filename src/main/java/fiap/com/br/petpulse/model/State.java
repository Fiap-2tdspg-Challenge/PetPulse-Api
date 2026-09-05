package fiap.com.br.petpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_CLY_ESTADO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_ESTADO")
    private String codEstado;
    @Column(name = "NOME_ESTADO")
    private String nomeEstado;
}
