package fiap.com.br.petpulse.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Parte da resposta do PetPulse-AI que a API Java usa para gravar o SmartAlert.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AiAnalysisResponse(
        String nivelRisco,
        boolean gerarAlerta,
        String tipoAlerta,
        String modo,
        Double scoreAnomalia,
        List<String> justificativa,
        Alert alerta
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Alert(
            String titulo,
            String mensagem,
            String recomendacao,
            String geradoPor
    ) {
    }
}
