package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.Enums.RecordType;
import fiap.com.br.petpulse.model.ClinicalHistory;

import java.time.LocalDate;

public record ClinicalHistoryResponse(
        Long id,
        RecordType recordType,
        String description,
        LocalDate recordDate,
        LocalDate returnDate,
        String clinicProfessional,
        String observations
) {
    public static ClinicalHistoryResponse fromEntity(ClinicalHistory clinicalHistory){
        return new ClinicalHistoryResponse(
                clinicalHistory.getId(),
                clinicalHistory.getRecordType(),
                clinicalHistory.getDescription(),
                clinicalHistory.getRecordDate(),
                clinicalHistory.getReturnDate(),
                clinicalHistory.getClinicProfessional(),
                clinicalHistory.getObservations()
        );
    }
}
