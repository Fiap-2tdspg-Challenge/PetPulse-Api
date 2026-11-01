package fiap.com.br.petpulse.ai;

import fiap.com.br.petpulse.config.AiServiceProperties;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Teste de contrato contra o PetPulse-AI rodando de verdade.
 * Só executa se PETPULSE_AI_URL estiver definida, por exemplo:
 * PETPULSE_AI_URL=http://localhost:8000 ./mvnw test -Dtest=AiAnalysisClientContractTest
 */
class AiAnalysisClientContractTest {

    @Test
    void aiServiceAcceptsJavaPayloadAndReturnsContextualAlert() {

        String url = System.getenv("PETPULSE_AI_URL");
        assumeTrue(url != null && !url.isBlank(), "PETPULSE_AI_URL não definida: teste de contrato ignorado");

        AiAnalysisClient client = new AiAnalysisClient(new AiServiceProperties(
                true, url, System.getenv().getOrDefault("PETPULSE_AI_KEY", ""), 15000, 14, 3000, 6, 60
        ));

        // 3 dias de leituras normais e, nas últimas 6 h, FC ~35% acima do padrão com o pet em repouso.
        Random random = new Random(42);
        LocalDateTime now = LocalDateTime.now().withNano(0);
        List<AiAnalysisRequest.Reading> readings = new ArrayList<>();
        for (LocalDateTime t = now.minusDays(3); !t.isAfter(now); t = t.plusMinutes(10)) {
            boolean episode = t.isAfter(now.minusHours(6));
            double activity = episode ? 8 : 5 + random.nextDouble() * 15;
            int heartRate = (int) Math.round((episode ? 112 : 78 + 0.5 * activity) + random.nextGaussian() * 3);
            readings.add(new AiAnalysisRequest.Reading(
                    t,
                    heartRate,
                    BigDecimal.valueOf(activity).setScale(1, java.math.RoundingMode.HALF_UP),
                    new BigDecimal("132"),
                    new BigDecimal("38.5")
            ));
        }

        AiAnalysisRequest request = new AiAnalysisRequest(
                new AiAnalysisRequest.Pet(1L, "Thor", "cao", "Labrador", "grande", 6.0, new BigDecimal("32.0"), "M"),
                readings,
                List.of(new AiAnalysisRequest.ClinicalEvent("CONSULTA", "Check-up", LocalDate.now().minusDays(20), null)),
                true
        );

        Optional<AiAnalysisResponse> response = client.analyze(request);

        assertThat(response).isPresent();
        assertThat(response.get().gerarAlerta()).isTrue();
        assertThat(response.get().nivelRisco()).isIn("MEDIO", "ALTO");
        assertThat(response.get().tipoAlerta()).isEqualTo("FREQUENCIA_CARDIACA");
        assertThat(response.get().modo()).isEqualTo("baseline_individual");
        assertThat(response.get().alerta().mensagem()).contains("Thor");
        assertThat(response.get().alerta().mensagem().length()).isLessThanOrEqualTo(500);
        assertThat(response.get().alerta().recomendacao().length()).isLessThanOrEqualTo(1000);
    }

    @Test
    void unreachableAiServiceReturnsEmptyInsteadOfFailing() {

        AiAnalysisClient client = new AiAnalysisClient(new AiServiceProperties(
                true, "http://localhost:1", "", 1000, 14, 3000, 6, 60
        ));

        AiAnalysisRequest request = new AiAnalysisRequest(
                new AiAnalysisRequest.Pet(1L, "Thor", "cao", null, null, null, null, null),
                List.of(),
                List.of(),
                true
        );

        assertThat(client.analyze(request)).isEmpty();
    }
}
