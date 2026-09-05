package fiap.com.br.petpulse.dto.request;

import fiap.com.br.petpulse.enums.DeviceStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IoTDeviceRequest(

        @NotNull(message = "O pet é obrigatório")
        @Positive(message = "O ID do pet deve ser maior que zero")
        Long petId,

        @Positive(message = "O intervalo de coleta deve ser maior que zero")
        Integer collectionIntervalMinutes,

        @Positive(message = "A frequência cardíaca deve ser maior que zero")
        Integer heartRate,

        @PositiveOrZero(message = "O nível de atividade não pode ser negativo")
        BigDecimal activityLevel,

        @Positive(message = "A pressão deve ser maior que zero")
        BigDecimal pressure,

        LocalDateTime lastReadingDate,

        @NotNull(message = "O status é obrigatório")
        DeviceStatus status

) {
}