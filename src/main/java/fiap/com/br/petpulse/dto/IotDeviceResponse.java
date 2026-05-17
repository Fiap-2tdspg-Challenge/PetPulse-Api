package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.Enums.DeviceStatus;
import fiap.com.br.petpulse.model.IoTDevice;

import java.time.LocalDate;

public record IotDeviceResponse(

        LocalDate linkedAt,

        Integer collectionIntervalMinutes,

        Integer heartRate,

        Double activityLevel,

        Double pressure,

        LocalDate lastReadingDate,

        DeviceStatus status
) {
    public static IotDeviceResponse fromEntity(IoTDevice device) {

        return new IotDeviceResponse(
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
