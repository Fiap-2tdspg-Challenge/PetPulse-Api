package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.assembler.IoTDeviceAssembler;
import fiap.com.br.petpulse.dto.request.IoTDeviceRequest;
import fiap.com.br.petpulse.dto.response.IoTDeviceResponse;
import fiap.com.br.petpulse.model.IoTDevice;
import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.repositories.IoTDeviceRepository;
import fiap.com.br.petpulse.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "iotDevices")
public class IoTDeviceService {

    private final IoTDeviceRepository ioTDeviceRepository;
    private final PetRepository petRepository;
    private final IoTDeviceAssembler ioTDeviceAssembler;


    @CacheEvict(allEntries = true)
    public IoTDeviceResponse addIoTDevice(IoTDeviceRequest request) {

        Pet pet = findPetById(request.petId());

        IoTDevice device = ioTDeviceAssembler.toEntity(
                request,
                pet
        );

        return ioTDeviceAssembler.toResponse(
                ioTDeviceRepository.save(device)
        );
    }


    @Cacheable
    public Page<IoTDeviceResponse> getAllIoTDevices(
            Pageable pageable
    ) {
        return ioTDeviceRepository.findAll(pageable)
                .map(ioTDeviceAssembler::toResponse);
    }


    @Cacheable
    public IoTDeviceResponse getIoTDeviceById(Long id) {
        return ioTDeviceAssembler.toResponse(
                findIoTDeviceById(id)
        );
    }


    @CacheEvict(allEntries = true)
    public IoTDeviceResponse updateIoTDevice(
            Long id,
            IoTDeviceRequest request
    ) {

        IoTDevice device = findIoTDeviceById(id);

        Pet pet = findPetById(request.petId());

        ioTDeviceAssembler.updateEntity(
                device,
                request,
                pet
        );

        return ioTDeviceAssembler.toResponse(
                ioTDeviceRepository.save(device)
        );
    }


    @CacheEvict(allEntries = true)
    public void deleteIoTDevice(Long id) {

        IoTDevice device = findIoTDeviceById(id);

        ioTDeviceRepository.delete(device);
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


    private Pet findPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Pet com id "
                                        + id
                                        + " não encontrado"
                        )
                );
    }
}