package fiap.com.br.petpulse.dto.response;

import fiap.com.br.petpulse.model.City;

public record CityResponse(
        Long id,
        String name,
        String stateCode,
        String stateName
) {
    public static CityResponse toResponse(City city) {
        return new CityResponse(
                city.getId(),
                city.getName(),
                city.getState().getCode(),
                city.getState().getName()
        );
    }
}
