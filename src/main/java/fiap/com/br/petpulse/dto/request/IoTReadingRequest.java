package fiap.com.br.petpulse.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record IoTReadingRequest(

        @NotNull
        @Positive
        Long deviceId,

        @Positive
        Integer heartRate,

        @PositiveOrZero
        BigDecimal activityLevel,

        @Positive
        BigDecimal pressure

) {
}