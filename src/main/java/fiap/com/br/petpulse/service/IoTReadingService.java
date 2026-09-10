package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.assembler.IoTReadingAssembler;
import fiap.com.br.petpulse.dto.request.IoTReadingRequest;
import fiap.com.br.petpulse.dto.response.IoTReadingResponse;
import fiap.com.br.petpulse.model.IoTDevice;
import fiap.com.br.petpulse.model.IoTReading;
import fiap.com.br.petpulse.repositories.IoTDeviceRepository;
import fiap.com.br.petpulse.repositories.IoTReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "iotReadings")
public class IoTReadingService {

    private final IoTReadingRepository ioTReadingRepository;
    private final IoTDeviceRepository ioTDeviceRepository;
    private final IoTReadingAssembler ioTReadingAssembler;
    private final IoTReadingAnalysisService ioTReadingAnalysisService;

    @CacheEvict(allEntries = true)
    @Transactional
    public IoTReadingResponse addIoTReading(
            IoTReadingRequest request
    ) {

        IoTDevice device = findIoTDeviceById(request.deviceId());

        IoTReading reading = ioTReadingAssembler.toEntity(
                request,
                device
        );

        IoTReading savedReading =
                ioTReadingRepository.save(reading);

        if (savedReading.getHeartRate() != null) {
            device.setHeartRate(savedReading.getHeartRate());
        }

        if (savedReading.getActivityLevel() != null) {
            device.setActivityLevel(savedReading.getActivityLevel());
        }

        if (savedReading.getPressure() != null) {
            device.setPressure(savedReading.getPressure());
        }

        device.setLastReadingDate(savedReading.getReadingDate());

        ioTDeviceRepository.save(device);

        ioTReadingAnalysisService.analyze(savedReading);

        return ioTReadingAssembler.toResponse(savedReading);
    }


    @Cacheable
    public Page<IoTReadingResponse> getAllIoTReadings(
            Pageable pageable
    ) {
        return ioTReadingRepository.findAll(pageable)
                .map(ioTReadingAssembler::toResponse);
    }


    @Cacheable
    public IoTReadingResponse getIoTReadingById(Long id) {
        return ioTReadingAssembler.toResponse(
                findIoTReadingById(id)
        );
    }

    public List<IoTReadingResponse> getLatestIoTReadingsByPetId(Long petId) {

        return ioTReadingRepository
                .findTop5ByDevicePetIdOrderByReadingDateDesc(petId)
                .stream()
                .map(ioTReadingAssembler::toResponse)
                .toList();
    }

    @CacheEvict(allEntries = true)
    public IoTReadingResponse updateIoTReading(
            Long id,
            IoTReadingRequest request
    ) {

        IoTReading reading = findIoTReadingById(id);

        IoTDevice device = findIoTDeviceById(request.deviceId());

        ioTReadingAssembler.updateEntity(
                reading,
                request,
                device
        );

        return ioTReadingAssembler.toResponse(
                ioTReadingRepository.save(reading)
        );
    }


    @CacheEvict(allEntries = true)
    public void deleteIoTReading(Long id) {

        IoTReading reading = findIoTReadingById(id);

        ioTReadingRepository.delete(reading);
    }


    private IoTReading findIoTReadingById(Long id) {
        return ioTReadingRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Leitura IoT com id "
                                        + id
                                        + " não encontrada"
                        )
                );
    }


    private IoTDevice findIoTDeviceById(Long id) {
        return ioTDeviceRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Dispositivo IoT com id "
                                        + id
                                        + " não encontrado"
                        )
                );
    }
}