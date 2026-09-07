package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.request.CityRequest;
import fiap.com.br.petpulse.dto.response.CityResponse;
import fiap.com.br.petpulse.model.City;
import fiap.com.br.petpulse.model.State;
import fiap.com.br.petpulse.repositories.CityRepository;
import fiap.com.br.petpulse.repositories.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    /**
     * Retorna a cidade existente com esse nome no estado informado (sem
     * diferenciar maiúsculas/minúsculas) ou cria uma nova caso ainda não exista.
     */
    public CityResponse findOrCreate(CityRequest request) {
        State state = findStateByCode(request.stateCode());
        String name = request.name().trim();

        City city = cityRepository.findByNameIgnoreCaseAndState_Code(name, state.getCode())
                .orElseGet(() -> {
                    Long nextId = cityRepository.findMaxId() + 1;
                    return cityRepository.save(
                            City.builder().id(nextId).state(state).name(name).build()
                    );
                });

        return CityResponse.toResponse(city);
    }

    public List<CityResponse> getCitiesByState(String stateCode) {
        return cityRepository.findByState_CodeIgnoreCase(stateCode)
                .stream()
                .map(CityResponse::toResponse)
                .toList();
    }

    private State findStateByCode(String code) {
        return stateRepository.findById(code.trim().toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estado com código " + code + " não encontrado"
                ));
    }
}
