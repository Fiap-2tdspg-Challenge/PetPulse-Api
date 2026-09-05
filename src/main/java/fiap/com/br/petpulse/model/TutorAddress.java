package fiap.com.br.petpulse.model;

import fiap.com.br.petpulse.config.BooleanToSNConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_CLY_ENDERECO_USUARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorAddress {

    @Id
    @Column(name = "SEQ_ENDERECO_USUARIO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "COD_TIPO_ENDERECO", nullable = false)
    private AddressType addressType;

    @ManyToOne
    @JoinColumn(name = "COD_CIDADE", nullable = false)
    private City city;

    @Column(name = "DES_ENDERECO", nullable = false, length = 150)
    private String address;

    @Column(name = "NUM_ENDERECO", length = 20)
    private String number;

    @Column(name = "DES_COMPLEMENTO", length = 100)
    private String complement;

    @Column(name = "NUM_CEP", length = 9)
    private String zipCode;

    @Column(name = "DES_BAIRRO", length = 100)
    private String neighborhood;

    @Convert(converter = BooleanToSNConverter.class)
    @Column(name = "STA_ATIVO", columnDefinition = "CHAR(1)")
    private Boolean active;

    @Column(name = "DAT_CADASTRO")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (active == null) {
            active = true;
        }

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}