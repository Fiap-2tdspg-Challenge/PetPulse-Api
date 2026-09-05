package fiap.com.br.petpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_CLY_TIPO_ALERTA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_ALERTA")
    private Long id;

    @Column(name = "DESCRICAO", nullable = false, length = 100)
    private String description;
}