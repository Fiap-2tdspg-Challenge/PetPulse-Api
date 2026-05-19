package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.IoTDeviceRequest;
import fiap.com.br.petpulse.dto.IoTDeviceResponse;
import fiap.com.br.petpulse.model.IoTDevice;
import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.repositories.IoTDeviceRepository;
import fiap.com.br.petpulse.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "iotDevices")
@Service
public class IoTDeviceService {

    @Autowired
    private IoTDeviceRepository ioTDeviceRepository;

    @Autowired
    private PetRepository petRepository;

    @CacheEvict
    public IoTDeviceResponse addIoTDevice(IoTDeviceRequest request) {

        Pet pet = petRepository.findById(request.petId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pet com id " + request.petId() + " não encontrado"
                )
        );

        IoTDevice ioTDevice = request.toEntity();
        ioTDevice.setPet(pet);

        return IoTDeviceResponse.toResponse(ioTDeviceRepository.save(ioTDevice));
    }

    @Cacheable
    public Page<IoTDeviceResponse> getAllIoTDevices(Pageable pageable) {
        return ioTDeviceRepository.findAll(pageable)
                .map(IoTDeviceResponse::toResponse);
    }

    @Cacheable
    public IoTDeviceResponse getIoTDeviceById(Long id) {
        return IoTDeviceResponse.toResponse(findIoTDeviceById(id));
    }

    @CacheEvict
    public void deleteIoTDevice(Long id) {
        findIoTDeviceById(id);
        ioTDeviceRepository.deleteById(id);
    }

    @CacheEvict
    public IoTDeviceResponse updateIoTDevice(Long id, IoTDeviceRequest request) {

        IoTDevice ioTDevice = findIoTDeviceById(id);

        Pet pet = petRepository.findById(request.petId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pet com id " + request.petId() + " não encontrado"
                )
        );

        ioTDevice.setCollectionIntervalMinutes(request.collectionIntervalMinutes());
        ioTDevice.setHeartRate(request.heartRate());
        ioTDevice.setActivityLevel(request.activityLevel());
        ioTDevice.setPressure(request.pressure());
        ioTDevice.setLastReadingDate(request.lastReadingDate());
        ioTDevice.setStatus(request.status());
        ioTDevice.setPet(pet);

        return IoTDeviceResponse.toResponse(ioTDeviceRepository.save(ioTDevice));
    }

    private IoTDevice findIoTDeviceById(Long id) {
        return ioTDeviceRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dispositivo IoT com id " + id + " não encontrado"
                )
        );
    }
}