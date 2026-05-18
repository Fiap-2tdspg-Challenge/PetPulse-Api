package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.enums.DeviceStatus;
import fiap.com.br.petpulse.model.IoTDevice;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record IoTDeviceRequest(

        @Positive
        Integer collectionIntervalMinutes,

        @Positive
        Integer heartRate,

        @PositiveOrZero
        Double activityLevel,

        @Positive
        Double pressure,

        LocalDate lastReadingDate,

        DeviceStatus status,

        @NotNull(message = "Pet id é requerido")
        Long petId

) {
    public IoTDevice toEntity(){
        return IoTDevice.builder()
                .collectionIntervalMinutes(collectionIntervalMinutes)
                .heartRate(heartRate)
                .activityLevel(activityLevel)
                .pressure(pressure)
                .lastReadingDate(lastReadingDate)
                .status(status)
                .build();
    }

}
