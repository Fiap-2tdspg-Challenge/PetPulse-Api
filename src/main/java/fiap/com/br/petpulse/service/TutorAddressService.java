package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.request.TutorAddressRequest;
import fiap.com.br.petpulse.dto.response.TutorAddressResponse;
import fiap.com.br.petpulse.model.AddressType;
import fiap.com.br.petpulse.model.City;
import fiap.com.br.petpulse.model.Tutor;
import fiap.com.br.petpulse.model.TutorAddress;
import fiap.com.br.petpulse.repositories.AddressTypeRepository;
import fiap.com.br.petpulse.repositories.CityRepository;
import fiap.com.br.petpulse.repositories.TutorAddressRepository;
import fiap.com.br.petpulse.repositories.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "tutorAddresses")
public class TutorAddressService {

    private final TutorAddressRepository tutorAddressRepository;
    private final TutorRepository tutorRepository;
    private final AddressTypeRepository addressTypeRepository;
    private final CityRepository cityRepository;

    @CacheEvict(allEntries = true)
    public TutorAddressResponse addTutorAddress(TutorAddressRequest request) {
        Tutor tutor = findTutorById(request.tutorId());
        AddressType addressType = findAddressTypeById(request.addressTypeId());
        City city = findCityById(request.cityId());

        Long nextId = tutorAddressRepository.findMaxId() + 1;

        TutorAddress address = TutorAddress.builder()
                .id(nextId)
                .tutor(tutor)
                .addressType(addressType)
                .city(city)
                .address(request.address())
                .number(request.number())
                .complement(request.complement())
                .zipCode(request.zipCode())
                .neighborhood(request.neighborhood())
                .build();

        return TutorAddressResponse.toResponse(tutorAddressRepository.save(address));
    }

    @Cacheable
    public Page<TutorAddressResponse> getAllTutorAddresses(Pageable pageable) {
        return tutorAddressRepository.findAll(pageable)
                .map(TutorAddressResponse::toResponse);
    }

    @Cacheable
    public TutorAddressResponse getTutorAddressById(Long id) {
        return TutorAddressResponse.toResponse(findTutorAddressById(id));
    }

    @CacheEvict(allEntries = true)
    public TutorAddressResponse updateTutorAddress(Long id, TutorAddressRequest request) {
        TutorAddress address = findTutorAddressById(id);
        AddressType addressType = findAddressTypeById(request.addressTypeId());
        City city = findCityById(request.cityId());

        address.setAddressType(addressType);
        address.setCity(city);
        address.setAddress(request.address());
        address.setNumber(request.number());
        address.setComplement(request.complement());
        address.setZipCode(request.zipCode());
        address.setNeighborhood(request.neighborhood());

        return TutorAddressResponse.toResponse(tutorAddressRepository.save(address));
    }

    @CacheEvict(allEntries = true)
    public void deleteTutorAddress(Long id) {
        TutorAddress address = findTutorAddressById(id);
        tutorAddressRepository.delete(address);
    }

    private TutorAddress findTutorAddressById(Long id) {
        return tutorAddressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Endereço com id " + id + " não encontrado"
                ));
    }

    private Tutor findTutorById(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tutor com id " + id + " não encontrado"
                ));
    }

    private AddressType findAddressTypeById(Integer id) {
        return addressTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tipo de endereço com id " + id + " não encontrado"
                ));
    }

    private City findCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cidade com id " + id + " não encontrada"
                ));
    }
}
