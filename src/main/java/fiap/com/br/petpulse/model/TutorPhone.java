package fiap.com.br.petpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_CLY_TELEFONE_USUARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorPhone {

    @Id
    @Column(name = "ID_TELEFONE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Tutor tutor;

    @Column(name = "NUMERO_TELEFONE", nullable = false, length = 20)
    private String phoneNumber;
}