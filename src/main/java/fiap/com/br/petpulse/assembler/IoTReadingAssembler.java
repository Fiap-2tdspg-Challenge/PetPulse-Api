package fiap.com.br.petpulse.assembler;

import fiap.com.br.petpulse.dto.request.IoTReadingRequest;
import fiap.com.br.petpulse.dto.response.IoTReadingResponse;
import fiap.com.br.petpulse.model.IoTDevice;
import fiap.com.br.petpulse.model.IoTReading;
import org.springframework.stereotype.Component;

@Component
public class IoTReadingAssembler {

    public IoTReading toEntity(
            IoTReadingRequest request,
            IoTDevice device
    ) {
        return IoTReading.builder()
                .device(device)
                .heartRate(request.heartRate())
                .activityLevel(request.activityLevel())
                .pressure(request.pressure())
                .build();
    }

    public IoTReadingResponse toResponse(IoTReading reading) {
        return new IoTReadingResponse(
                reading.getId(),
                reading.getDevice().getId(),
                reading.getDevice().getPet().getId(),
                reading.getDevice().getPet().getName(),
                reading.getReadingDate(),
                reading.getHeartRate(),
                reading.getActivityLevel(),
                reading.getPressure()
        );
    }

    public void updateEntity(
            IoTReading reading,
            IoTReadingRequest request,
            IoTDevice device
    ) {
        reading.setDevice(device);
        reading.setHeartRate(request.heartRate());
        reading.setActivityLevel(request.activityLevel());
        reading.setPressure(request.pressure());
    }
}