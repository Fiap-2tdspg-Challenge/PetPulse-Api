package fiap.com.br.petpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_CLY_TIPO_ENDERECO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressType {

    @Id
    @Column(name = "COD_TIPO_ENDERECO")
    private Integer id;

    @Column(name = "DES_TIPO_ENDERECO", nullable = false, length = 50)
    private String description;
}