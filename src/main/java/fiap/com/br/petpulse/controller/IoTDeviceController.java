package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.IoTDeviceRequest;
import fiap.com.br.petpulse.dto.IoTDeviceResponse;
import fiap.com.br.petpulse.service.IoTDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/iot-devices")
@Tag(name = "IoT Device", description = "Endpoints para gerenciamento dos dispositivos IoT vinculados aos pets")
public class IoTDeviceController {

    @Autowired
    private IoTDeviceService ioTDeviceService;

    @PostMapping
    @Operation(
            summary = "Cadastrar dispositivo IoT",
            description = "Cria um novo dispositivo IoT vinculado a um pet existente. O pet deve ser informado pelo campo petId."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dispositivo IoT cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado")
    })
    public IoTDeviceResponse addIoTDevice(@RequestBody @Valid IoTDeviceRequest request) {
        return ioTDeviceService.addIoTDevice(request);
    }

    @GetMapping
    @Operation(
            summary = "Listar dispositivos IoT",
            description = "Retorna uma lista paginada de dispositivos IoT cadastrados. Permite paginação e ordenação por parâmetros como page, size e sort."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispositivos IoT listados com sucesso")
    })
    public Page<IoTDeviceResponse> getAllIoTDevices(Pageable pageable) {
        return ioTDeviceService.getAllIoTDevices(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar dispositivo IoT por ID",
            description = "Retorna os dados de um dispositivo IoT específico a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispositivo IoT encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dispositivo IoT não encontrado")
    })
    public IoTDeviceResponse getIoTDeviceById(@PathVariable Long id) {
        return ioTDeviceService.getIoTDeviceById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar dispositivo IoT",
            description = "Atualiza os dados de um dispositivo IoT existente, incluindo leituras, status e vínculo com o pet."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispositivo IoT atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Dispositivo IoT ou pet não encontrado")
    })
    public IoTDeviceResponse updateIoTDevice(
            @PathVariable Long id,
            @RequestBody @Valid IoTDeviceRequest request
    ) {
        return ioTDeviceService.updateIoTDevice(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar dispositivo IoT",
            description = "Remove um dispositivo IoT do sistema a partir do ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dispositivo IoT removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dispositivo IoT não encontrado")
    })
    public void deleteIoTDevice(@PathVariable Long id) {
        ioTDeviceService.deleteIoTDevice(id);
    }
}