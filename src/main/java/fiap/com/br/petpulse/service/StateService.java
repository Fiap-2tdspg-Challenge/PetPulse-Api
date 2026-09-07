package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.request.StateRequest;
import fiap.com.br.petpulse.dto.response.StateResponse;
import fiap.com.br.petpulse.model.State;
import fiap.com.br.petpulse.repositories.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository stateRepository;

    /**
     * Retorna o estado existente com esse código (UF) ou cria um novo caso
     * ainda não exista.
     */
    public StateResponse findOrCreate(StateRequest request) {
        String code = request.code().trim().toUpperCase();

        State state = stateRepository.findById(code)
                .orElseGet(() -> stateRepository.save(
                        State.builder().code(code).name(request.name().trim()).build()
                ));

        return StateResponse.toResponse(state);
    }

    public List<StateResponse> getAllStates() {
        return stateRepository.findAll(Sort.by("name"))
                .stream()
                .map(StateResponse::toResponse)
                .toList();
    }
}
