package fiap.com.br.petpulse.dto.response;

public record BreedResponse(
        Long id,
        Long speciesId,
        String speciesName,
        String name
) {
}
