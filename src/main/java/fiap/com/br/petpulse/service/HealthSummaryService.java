package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.response.HealthSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthSummaryService {

    private final PetService petService;
    private final ClinicalHistoryService clinicalHistoryService;
    private final IoTReadingService ioTReadingService;
    private final SmartAlertService smartAlertService;

    public HealthSummaryResponse getHealthSummary(Long petId) {

        return new HealthSummaryResponse(
                petService.getPetById(petId),
                clinicalHistoryService.getClinicalHistoriesByPetId(petId),
                ioTReadingService.getLatestIoTReadingsByPetId(petId),
                smartAlertService.getSmartAlertsByPetId(petId)
        );
    }
}