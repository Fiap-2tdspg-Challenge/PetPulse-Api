package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.dto.request.IoTReadingRequest;
import fiap.com.br.petpulse.dto.response.IoTReadingResponse;
import fiap.com.br.petpulse.service.IoTReadingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iot-readings")
@RequiredArgsConstructor
@Tag(
        name = "IoT Reading",
        description = "Endpoints para gerenciamento das leituras coletadas pelos dispositivos IoT"
)
public class IoTReadingController {

    private final IoTReadingService ioTReadingService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastrar leitura IoT",
            description = "Cria uma nova leitura vinculada a um dispositivo IoT existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Leitura IoT cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Dispositivo IoT não encontrado")
    })
    public IoTReadingResponse addIoTReading(
            @RequestBody @Valid IoTReadingRequest request
    ) {
        return ioTReadingService.addIoTReading(request);
    }


    @GetMapping
    @Operation(
            summary = "Listar leituras IoT",
            description = "Retorna uma lista paginada de leituras IoT cadastradas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leituras IoT listadas com sucesso")
    })
    public Page<IoTReadingResponse> getAllIoTReadings(
            Pageable pageable
    ) {
        return ioTReadingService.getAllIoTReadings(pageable);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar leitura IoT por ID",
            description = "Retorna os dados de uma leitura IoT específica a partir do seu identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leitura IoT encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Leitura IoT não encontrada")
    })
    public IoTReadingResponse getIoTReadingById(
            @PathVariable Long id
    ) {
        return ioTReadingService.getIoTReadingById(id);
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar leitura IoT",
            description = "Atualiza os dados de uma leitura IoT existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leitura IoT atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Leitura IoT ou dispositivo IoT não encontrado"
            )
    })
    public IoTReadingResponse updateIoTReading(
            @PathVariable Long id,
            @RequestBody @Valid IoTReadingRequest request
    ) {
        return ioTReadingService.updateIoTReading(id, request);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Deletar leitura IoT",
            description = "Remove uma leitura IoT do sistema a partir do ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Leitura IoT removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Leitura IoT não encontrada")
    })
    public void deleteIoTReading(
            @PathVariable Long id
    ) {
        ioTReadingService.deleteIoTReading(id);
    }
}