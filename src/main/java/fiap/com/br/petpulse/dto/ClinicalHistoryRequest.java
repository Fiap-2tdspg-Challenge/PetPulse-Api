package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.Enums.RecordType;
import fiap.com.br.petpulse.model.ClinicalHistory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClinicalHistoryRequest(

        @NotNull(message = "Tipo da Histórioco é Obrigatorio")
        RecordType recordType,

        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 500)
        String description,

        LocalDate returnDate,

        @Size(max = 150)
        String clinicProfessional,

        @Size(max = 1000)
        String observations

) {
    public ClinicalHistory toEntity(){
        return ClinicalHistory.builder()
                .recordType(recordType)
                .description(description)
                .recordDate(returnDate)
                .clinicProfessional(clinicProfessional)
                .observations(observations)
                .build();
    }
}
