package fiap.com.br.petpulse.ai;

import fiap.com.br.petpulse.config.AiServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.Duration;
import java.util.Optional;

/**
 * Cliente HTTP do microsserviço PetPulse-AI.
 *
 * Qualquer falha (serviço fora do ar, timeout, resposta inválida) devolve Optional.empty(),
 * e quem chama aplica as regras fixas como contingência — a leitura nunca deixa de ser salva.
 */
@Slf4j
@Component
public class AiAnalysisClient {

    private final AiServiceProperties properties;
    private final RestClient restClient;

    public AiAnalysisClient(AiServiceProperties properties) {
        this.properties = properties;

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofMillis(properties.timeoutMs()));
        requestFactory.setReadTimeout(Duration.ofMillis(properties.timeoutMs()));

        this.restClient = RestClient.builder()
                .baseUrl(properties.baseUrl())
                .requestFactory(requestFactory)
                .build();
    }

    public Optional<AiAnalysisResponse> analyze(AiAnalysisRequest request) {

        if (!properties.enabled()) {
            return Optional.empty();
        }

        try {
            AiAnalysisResponse response = restClient.post()
                    .uri("/v1/analises")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(headers -> {
                        if (properties.apiKey() != null && !properties.apiKey().isBlank()) {
                            headers.set("X-API-Key", properties.apiKey());
                        }
                    })
                    .body(request)
                    .retrieve()
                    .body(AiAnalysisResponse.class);

            return Optional.ofNullable(response);

        } catch (RestClientException exception) {
            log.warn(
                    "PetPulse-AI indisponível ({}). Usando regras fixas para o pet {}.",
                    exception.getMessage(),
                    request.pet().id()
            );
            return Optional.empty();
        }
    }
}
