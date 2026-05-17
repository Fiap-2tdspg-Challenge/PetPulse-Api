package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.Enums.DeviceStatus;
import fiap.com.br.petpulse.model.IoTDevice;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record IoTDeviceRequest(

        LocalDate linkedAt,

        @Positive
        Integer collectionIntervalMinutes,

        @Positive
        Integer heartRate,

        @PositiveOrZero
        Double activityLevel,

        @Positive
        Double pressure,

        LocalDate lastReadingDate,

        DeviceStatus status

) {
    public IoTDevice toEntity(){
        return IoTDevice.builder()
                .linkedAt(linkedAt)
                .collectionIntervalMinutes(collectionIntervalMinutes)
                .heartRate(heartRate)
                .activityLevel(activityLevel)
                .pressure(pressure)
                .lastReadingDate(lastReadingDate)
                .status(status)
                .build();
    }

}
