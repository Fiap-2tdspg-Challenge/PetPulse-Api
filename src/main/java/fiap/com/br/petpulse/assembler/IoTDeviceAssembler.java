package fiap.com.br.petpulse.assembler;

import fiap.com.br.petpulse.dto.request.IoTDeviceRequest;
import fiap.com.br.petpulse.dto.response.IoTDeviceResponse;
import fiap.com.br.petpulse.model.IoTDevice;
import fiap.com.br.petpulse.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class IoTDeviceAssembler {

    public IoTDevice toEntity(IoTDeviceRequest request, Pet pet) {
        return IoTDevice.builder()
                .pet(pet)
                .collectionIntervalMinutes(request.collectionIntervalMinutes())
                .heartRate(request.heartRate())
                .activityLevel(request.activityLevel())
                .pressure(request.pressure())
                .lastReadingDate(request.lastReadingDate())
                .status(request.status())
                .build();
    }

    public IoTDeviceResponse toResponse(IoTDevice device) {
        return new IoTDeviceResponse(
                device.getId(),
                device.getPet().getId(),
                device.getPet().getName(),
                device.getLinkedAt(),
                device.getCollectionIntervalMinutes(),
                device.getHeartRate(),
                device.getActivityLevel(),
                device.getPressure(),
                device.getLastReadingDate(),
                device.getStatus()
        );
    }

    public void updateEntity(
            IoTDevice device,
            IoTDeviceRequest request,
            Pet pet
    ) {
        device.setPet(pet);
        device.setCollectionIntervalMinutes(request.collectionIntervalMinutes());
        device.setHeartRate(request.heartRate());
        device.setActivityLevel(request.activityLevel());
        device.setPressure(request.pressure());
        device.setLastReadingDate(request.lastReadingDate());
        device.setStatus(request.status());
    }
}