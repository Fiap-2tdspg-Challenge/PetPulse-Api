package fiap.com.br.petpulse.dto.response;

import fiap.com.br.petpulse.model.TutorAddress;

public record TutorAddressResponse(
        Long id,

        Long tutorId,

        Integer addressTypeId,
        String addressTypeDescription,

        Long cityId,
        String cityName,
        String stateCode,
        String stateName,

        String address,
        String number,
        String complement,
        String zipCode,
        String neighborhood
) {
    public static TutorAddressResponse toResponse(TutorAddress address) {
        return new TutorAddressResponse(
                address.getId(),

                address.getTutor().getId(),

                address.getAddressType().getId(),
                address.getAddressType().getDescription(),

                address.getCity().getId(),
                address.getCity().getName(),
                address.getCity().getState().getCode(),
                address.getCity().getState().getName(),

                address.getAddress(),
                address.getNumber(),
                address.getComplement(),
                address.getZipCode(),
                address.getNeighborhood()
        );
    }
}
