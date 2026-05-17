package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.model.IoTDevice;
import fiap.com.br.petpulse.repositories.IoTDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IoTDeviceService {
    @Autowired
    private IoTDeviceRepository ioTDeviceRepository;

    public IoTDevice addIoTDevice(IoTDevice ioTDevice){
        return ioTDeviceRepository.save(ioTDevice);
    }

    public List<IoTDevice> getAllIoTDevices() {
        return ioTDeviceRepository.findAll();
    }

    public IoTDevice getIoTDeviceById(Long id){
        return findIoTDeviceById(id);
    }

    public void deleteIoTDevice(Long id) {
        findIoTDeviceById(id);
        ioTDeviceRepository.deleteById(id);
    }

    public IoTDevice updateIoTDevice(Long id, IoTDevice newIoTDevice) {
        findIoTDeviceById(id);
        newIoTDevice.setId(id);
        return ioTDeviceRepository.save(newIoTDevice);
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
