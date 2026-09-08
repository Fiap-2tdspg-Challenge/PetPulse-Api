package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.response.PetSizeResponse;
import fiap.com.br.petpulse.repositories.PetSizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetSizeService {

    private final PetSizeRepository petSizeRepository;

    public List<PetSizeResponse> getAllPetSizes() {
        return petSizeRepository.findAll()
                .stream()
                .map(PetSizeResponse::toResponse)
                .toList();
    }
}
