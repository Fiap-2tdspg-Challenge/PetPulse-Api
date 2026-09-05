package fiap.com.br.petpulse.dto.request;

import fiap.com.br.petpulse.enums.RecordType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClinicalHistoryRequest(

        @NotNull(message = "O pet é obrigatório")
        @Positive(message = "O ID do pet deve ser maior que zero")
        Long petId,

        @Positive(message = "O ID do profissional deve ser maior que zero")
        Long professionalId,

        RecordType recordType,

        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
        String description,

        LocalDate returnDate,

        @Size(max = 1000, message = "As observações devem ter no máximo 1000 caracteres")
        String observations

) {
}