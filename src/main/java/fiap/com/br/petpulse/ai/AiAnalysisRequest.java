package fiap.com.br.petpulse.ai;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Contrato do POST /v1/analises do PetPulse-AI (JSON em camelCase, em português,
 * o mesmo padrão do payload da coleira).
 */
public record AiAnalysisRequest(
        Pet pet,
        List<Reading> leituras,
        List<ClinicalEvent> historicoClinico,
        boolean gerarTexto
) {

    public record Pet(
            Long id,
            String nome,
            String especie,
            String raca,
            String porte,
            Double idadeAnos,
            BigDecimal pesoKg,
            String sexo
    ) {
    }

    public record Reading(
            LocalDateTime dataLeitura,
            Integer frequenciaCardiaca,
            BigDecimal nivelAtividade,
            BigDecimal pressao,
            BigDecimal temperatura
    ) {
    }

    public record ClinicalEvent(
            String tipo,
            String descricao,
            LocalDate data,
            LocalDate dataRetorno
    ) {
    }
}
