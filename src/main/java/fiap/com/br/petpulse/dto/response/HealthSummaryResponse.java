package fiap.com.br.petpulse.dto.response;

import java.util.List;

public record HealthSummaryResponse(

        PetResponse pet,

        List<ClinicalHistoryResponse> clinicalHistories,

        List<IoTReadingResponse> latestIoTReadings,

        List<SmartAlertResponse> alerts

) {
}