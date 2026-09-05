package fiap.com.br.petpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_CLY_CIDADE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City {

    @Id
    @Column(name = "COD_CIDADE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COD_ESTADO", nullable = false, columnDefinition = "CHAR(2)")
    private State state;

    @Column(name = "NOME_CIDADE", nullable = false, length = 100)
    private String name;
}