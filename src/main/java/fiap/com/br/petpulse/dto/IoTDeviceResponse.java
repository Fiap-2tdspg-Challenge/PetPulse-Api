package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.enums.DeviceStatus;
import fiap.com.br.petpulse.model.IoTDevice;

import java.time.LocalDate;

public record IoTDeviceResponse(

        LocalDate linkedAt,

        Integer collectionIntervalMinutes,

        Integer heartRate,

        Double activityLevel,

        Double pressure,

        LocalDate lastReadingDate,

        DeviceStatus status
) {
    public static IoTDeviceResponse toResponse(IoTDevice device) {

        return new IoTDeviceResponse(
                device.getLinkedAt(),
                device.getCollectionIntervalMinutes(),
                device.getHeartRate(),
                device.getActivityLevel(),
                device.getPressure(),
                device.getLastReadingDate(),
                device.getStatus()
        );
    }
}
