package fiap.com.br.petpulse.dto.response;

import fiap.com.br.petpulse.model.PetSize;

public record PetSizeResponse(
        Long id,
        String description
) {
    public static PetSizeResponse toResponse(PetSize petSize) {
        return new PetSizeResponse(petSize.getId(), petSize.getDescription());
    }
}
