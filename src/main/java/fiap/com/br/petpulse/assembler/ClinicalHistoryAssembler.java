package fiap.com.br.petpulse.assembler;

import fiap.com.br.petpulse.dto.request.ClinicalHistoryRequest;
import fiap.com.br.petpulse.dto.response.ClinicalHistoryResponse;
import fiap.com.br.petpulse.model.ClinicalHistory;
import fiap.com.br.petpulse.model.Pet;
import fiap.com.br.petpulse.model.Professional;
import org.springframework.stereotype.Component;

@Component
public class ClinicalHistoryAssembler {

    public ClinicalHistory toEntity(
            ClinicalHistoryRequest request,
            Pet pet,
            Professional professional
    ) {
        return ClinicalHistory.builder()
                .pet(pet)
                .professional(professional)
                .recordType(request.recordType())
                .description(request.description())
                .returnDate(request.returnDate())
                .observations(request.observations())
                .build();
    }

    public ClinicalHistoryResponse toResponse(ClinicalHistory history) {

        Professional professional = history.getProfessional();

        return new ClinicalHistoryResponse(
                history.getId(),

                history.getPet().getId(),
                history.getPet().getName(),

                professional != null ? professional.getId() : null,
                professional != null ? professional.getName() : null,
                professional != null ? professional.getCrmv() : null,

                history.getRecordType(),
                history.getDescription(),
                history.getRecordDate(),
                history.getReturnDate(),
                history.getObservations()
        );
    }

    public void updateEntity(
            ClinicalHistory history,
            ClinicalHistoryRequest request,
            Pet pet,
            Professional professional
    ) {
        history.setPet(pet);
        history.setProfessional(professional);
        history.setRecordType(request.recordType());
        history.setDescription(request.description());
        history.setReturnDate(request.returnDate());
        history.setObservations(request.observations());
    }
}