package fiap.com.br.petpulse.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IoTReadingRequest(

        @NotNull
        @Positive
        Long deviceId,

        @Positive
        Integer heartRate,

        @PositiveOrZero
        BigDecimal activityLevel,

        @Positive
        BigDecimal pressure,

        @DecimalMin(value = "30.0", message = "A temperatura deve ser de no mínimo 30 °C")
        @DecimalMax(value = "45.0", message = "A temperatura deve ser de no máximo 45 °C")
        BigDecimal temperature,

        // Opcional: leituras guardadas pela coleira sem conexão chegam com a data original.
        @PastOrPresent(message = "A data da leitura não pode estar no futuro")
        LocalDateTime readingDate

) {
}