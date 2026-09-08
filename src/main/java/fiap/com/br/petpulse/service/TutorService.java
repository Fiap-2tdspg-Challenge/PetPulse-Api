package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.request.TutorRequest;
import fiap.com.br.petpulse.dto.response.TutorResponse;
import fiap.com.br.petpulse.model.Tutor;
import fiap.com.br.petpulse.repositories.PetRepository;
import fiap.com.br.petpulse.repositories.TutorAddressRepository;
import fiap.com.br.petpulse.repositories.TutorPhoneRepository;
import fiap.com.br.petpulse.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "tutors")
@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private TutorPhoneRepository tutorPhoneRepository;

    @Autowired
    private TutorAddressRepository tutorAddressRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @CacheEvict
    public TutorResponse addTutor(TutorRequest request) {
        Tutor tutor = request.toEntity();
        tutor.setPassword(passwordEncoder.encode(request.password()));

        return TutorResponse.toResponse(tutorRepository.save(tutor));
    }

    @Cacheable
    public Page<TutorResponse> getAllTutors(Pageable pageable) {
        return tutorRepository.findAll(pageable)
                .map(TutorResponse::toResponse);
    }

    @Cacheable
    public TutorResponse getTutorById(Long id) {
        return TutorResponse.toResponse(findTutorById(id));
    }

    /**
     * Exclui o tutor e seus dados dependentes (telefones, endereços). Pets
     * têm histórico clínico, alertas e dispositivos IoT vinculados, então a
     * exclusão em cascata não é automática aqui — o tutor precisa remover os
     * pets primeiro (a API bloqueia com 409 caso ainda existam).
     */
    @CacheEvict
    public void deleteTutor(Long id) {
        findTutorById(id);

        if (petRepository.existsByTutor_Id(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir o tutor: existem pets cadastrados para ele. Remova os pets primeiro."
            );
        }

        tutorPhoneRepository.deleteAll(tutorPhoneRepository.findByTutor_Id(id));
        tutorAddressRepository.deleteAll(tutorAddressRepository.findByTutor_Id(id));

        tutorRepository.deleteById(id);
    }

    @CacheEvict
    public TutorResponse updateTutor(Long id, TutorRequest request) {
        Tutor tutor = findTutorById(id);

        tutor.setName(request.name());
        tutor.setCpf(request.cpf());
        tutor.setEmail(request.email());
        tutor.setPassword(passwordEncoder.encode(request.password()));

        return TutorResponse.toResponse(tutorRepository.save(tutor));
    }

    private Tutor findTutorById(Long id) {
        return tutorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tutor com id " + id + " não encontrado"
                )
        );
    }

    @Cacheable
    public List<TutorResponse> searchTutorsByName(String name) {
        return tutorRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(TutorResponse::toResponse)
                .toList();
    }
}
