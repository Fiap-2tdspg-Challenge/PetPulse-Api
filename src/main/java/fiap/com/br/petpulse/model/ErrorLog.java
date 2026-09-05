package fiap.com.br.petpulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_CLY_LOG_ERRO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOG")
    private Long id;

    @Column(name = "NOME_PROCEDURE", length = 100)
    private String procedureName;

    @Column(name = "CODIGO_ERRO")
    private Integer errorCode;

    @Column(name = "MENSAGEM_ERRO", length = 4000)
    private String errorMessage;

    @Column(name = "DATA_ERRO")
    private LocalDateTime errorDate;

    @Column(name = "USUARIO_BANCO", length = 100)
    private String databaseUser;
}