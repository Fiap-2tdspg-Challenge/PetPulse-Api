package fiap.com.br.petpulse.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IoTReadingResponse(

        Long id,
        Long deviceId,
        Long petId,
        String petName,
        LocalDateTime readingDate,
        Integer heartRate,
        BigDecimal activityLevel,
        BigDecimal pressure

) {
}