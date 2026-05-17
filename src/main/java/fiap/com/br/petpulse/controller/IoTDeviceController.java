package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.model.IoTDevice;
import fiap.com.br.petpulse.service.IoTDeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iot-devices")
public class IoTDeviceController {

    @Autowired
    private IoTDeviceService ioTDeviceService;

    @PostMapping
    public IoTDevice addIoTDevice(@RequestBody @Valid IoTDevice ioTDevice) {
        return ioTDeviceService.addIoTDevice(ioTDevice);
    }

    @GetMapping
    public List<IoTDevice> getAllIoTDevices() {
        return ioTDeviceService.getAllIoTDevices();
    }

    @GetMapping("/{id}")
    public IoTDevice getIoTDeviceById(@PathVariable Long id) {
        return ioTDeviceService.getIoTDeviceById(id);
    }

    @PutMapping("/{id}")
    public IoTDevice updateIoTDevice(
            @PathVariable Long id,
            @RequestBody @Valid IoTDevice ioTDevice
    ) {
        return ioTDeviceService.updateIoTDevice(id, ioTDevice);
    }

    @DeleteMapping("/{id}")
    public void deleteIoTDevice(@PathVariable Long id) {
        ioTDeviceService.deleteIoTDevice(id);
    }
}
