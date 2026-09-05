package fiap.com.br.petpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_CLY_PROFISSIONAL")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROFISSIONAL")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CLINICA")
    private Clinic clinic;

    @Column(name = "NOME_PROFISSIONAL", nullable = false, length = 150)
    private String name;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "SENHA", nullable = false, length = 255)
    private String password;

    @Column(name = "CRMV", nullable = false, unique = true, length = 20)
    private String crmv;

    @Column(name = "DT_CADASTRO", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}