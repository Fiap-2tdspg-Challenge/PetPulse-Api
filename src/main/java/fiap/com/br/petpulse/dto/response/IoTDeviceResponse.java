package fiap.com.br.petpulse.dto.response;

import fiap.com.br.petpulse.enums.DeviceStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IoTDeviceResponse(

        Long id,
        Long petId,
        String petName,
        LocalDateTime linkedAt,
        Integer collectionIntervalMinutes,
        Integer heartRate,
        BigDecimal activityLevel,
        BigDecimal pressure,
        LocalDateTime lastReadingDate,
        DeviceStatus status

) {
}