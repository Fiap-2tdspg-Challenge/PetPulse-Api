package fiap.com.br.petpulse.dto.response;

import fiap.com.br.petpulse.model.State;

public record StateResponse(
        String code,
        String name
) {
    public static StateResponse toResponse(State state) {
        return new StateResponse(state.getCode(), state.getName());
    }
}
