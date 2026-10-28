package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.request.TutorRequest;
import fiap.com.br.petpulse.dto.response.TutorResponse;
import fiap.com.br.petpulse.model.Tutor;
import fiap.com.br.petpulse.repositories.PetRepository;
import fiap.com.br.petpulse.repositories.TutorAddressRepository;
import fiap.com.br.petpulse.repositories.TutorPhoneRepository;
import fiap.com.br.petpulse.repositories.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

@CacheConfig(cacheNames = "tutors")
@Service
@RequiredArgsConstructor
public class TutorService {

    private final TutorRepository tutorRepository;

    private final TutorPhoneRepository tutorPhoneRepository;

    private final TutorAddressRepository tutorAddressRepository;

    private final PetRepository petRepository;

    private final PasswordEncoder passwordEncoder;

    @CacheEvict(allEntries = true)
    public TutorResponse addTutor(TutorRequest request) {
        validateUniqueEmailAndCpf(
                tutorRepository.existsByEmailIgnoreCase(request.email()),
                tutorRepository.existsByCpf(request.cpf())
        );

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
    @Caching(evict = {
            @CacheEvict(cacheNames = "tutors", allEntries = true),
            @CacheEvict(cacheNames = "tutorPhones", allEntries = true),
            @CacheEvict(cacheNames = "tutorAddresses", allEntries = true)
    })
    @Transactional
    public void deleteTutor(Long id, String loggedEmail) {
        Tutor tutor = findTutorById(id);

        validateLoggedTutor(tutor, loggedEmail);

        if (petRepository.existsByTutor_Id(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir o tutor: existem pets cadastrados para ele. Remova os pets primeiro."
            );
        }

        tutorPhoneRepository.deleteAll(
                tutorPhoneRepository.findByTutor_Id(id)
        );

        tutorAddressRepository.deleteAll(
                tutorAddressRepository.findByTutor_Id(id)
        );

        tutorRepository.deleteById(id);
    }

    @CacheEvict(allEntries = true)
    public TutorResponse updateTutor(Long id, TutorRequest request, String loggedEmail) {
        Tutor tutor = findTutorById(id);

        validateLoggedTutor(tutor, loggedEmail);

        validateUniqueEmailAndCpf(
                tutorRepository.existsByEmailIgnoreCaseAndIdNot(request.email(), id),
                tutorRepository.existsByCpfAndIdNot(request.cpf(), id)
        );

        tutor.setName(request.name());
        tutor.setCpf(request.cpf());
        tutor.setEmail(request.email());
        tutor.setPassword(passwordEncoder.encode(request.password()));

        return TutorResponse.toResponse(tutorRepository.save(tutor));
    }

    public TutorResponse getTutorByEmail(String email) {
        return tutorRepository.findByEmailIgnoreCase(email)
                .map(TutorResponse::toResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tutor com e-mail " + email + " não encontrado"
                ));
    }

    private void validateLoggedTutor(Tutor tutor, String loggedEmail) {
        if (!tutor.getEmail().equalsIgnoreCase(loggedEmail)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você só pode alterar ou excluir a sua própria conta"
            );
        }
    }

    private void validateUniqueEmailAndCpf(boolean emailInUse, boolean cpfInUse) {
        if (emailInUse) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }
        if (cpfInUse) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
        }
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
