package fiap.com.br.petpulse.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "petpulse.ai")
public record AiServiceProperties(
        boolean enabled,
        String baseUrl,
        String apiKey,
        int timeoutMs,
        int historyDays,
        int maxReadings,
        int cooldownHours,
        int liveWindowMinutes
) {
}
