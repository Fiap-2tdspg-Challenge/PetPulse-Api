package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.IoTDeviceRequest;
import fiap.com.br.petpulse.dto.IoTDeviceResponse;
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
    public IoTDeviceResponse addIoTDevice(@RequestBody @Valid IoTDeviceRequest request) {
        return ioTDeviceService.addIoTDevice(request);
    }

    @GetMapping
    public List<IoTDeviceResponse> getAllIoTDevices() {
        return ioTDeviceService.getAllIoTDevices();
    }

    @GetMapping("/{id}")
    public IoTDeviceResponse getIoTDeviceById(@PathVariable Long id) {
        return ioTDeviceService.getIoTDeviceById(id);
    }

    @PutMapping("/{id}")
    public IoTDeviceResponse updateIoTDevice(
            @PathVariable Long id,
            @RequestBody @Valid IoTDeviceRequest request
    ) {
        return ioTDeviceService.updateIoTDevice(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteIoTDevice(@PathVariable Long id) {
        ioTDeviceService.deleteIoTDevice(id);
    }
}