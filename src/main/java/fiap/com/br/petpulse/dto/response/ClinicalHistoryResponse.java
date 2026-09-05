package fiap.com.br.petpulse.dto.response;

import fiap.com.br.petpulse.enums.RecordType;

import java.time.LocalDate;

public record ClinicalHistoryResponse(

        Long id,

        Long petId,
        String petName,

        Long professionalId,
        String professionalName,
        String professionalCrmv,

        RecordType recordType,

        String description,

        LocalDate recordDate,

        LocalDate returnDate,

        String observations

) {
}