package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.request.TutorPhoneRequest;
import fiap.com.br.petpulse.dto.response.TutorPhoneResponse;
import fiap.com.br.petpulse.model.Tutor;
import fiap.com.br.petpulse.model.TutorPhone;
import fiap.com.br.petpulse.repositories.TutorPhoneRepository;
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
@CacheConfig(cacheNames = "tutorPhones")
public class TutorPhoneService {

    private final TutorPhoneRepository tutorPhoneRepository;
    private final TutorRepository tutorRepository;

    @CacheEvict(allEntries = true)
    public TutorPhoneResponse addTutorPhone(TutorPhoneRequest request) {
        Tutor tutor = findTutorById(request.tutorId());

        Long nextId = tutorPhoneRepository.findMaxId() + 1;

        TutorPhone phone = TutorPhone.builder()
                .id(nextId)
                .tutor(tutor)
                .phoneNumber(request.phoneNumber())
                .build();

        return TutorPhoneResponse.toResponse(tutorPhoneRepository.save(phone));
    }

    @Cacheable
    public Page<TutorPhoneResponse> getAllTutorPhones(Pageable pageable) {
        return tutorPhoneRepository.findAll(pageable)
                .map(TutorPhoneResponse::toResponse);
    }

    @Cacheable
    public TutorPhoneResponse getTutorPhoneById(Long id) {
        return TutorPhoneResponse.toResponse(findTutorPhoneById(id));
    }

    @CacheEvict(allEntries = true)
    public TutorPhoneResponse updateTutorPhone(Long id, TutorPhoneRequest request) {
        TutorPhone phone = findTutorPhoneById(id);
        phone.setPhoneNumber(request.phoneNumber());

        return TutorPhoneResponse.toResponse(tutorPhoneRepository.save(phone));
    }

    @CacheEvict(allEntries = true)
    public void deleteTutorPhone(Long id) {
        TutorPhone phone = findTutorPhoneById(id);
        tutorPhoneRepository.delete(phone);
    }

    private TutorPhone findTutorPhoneById(Long id) {
        return tutorPhoneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Telefone com id " + id + " não encontrado"
                ));
    }

    private Tutor findTutorById(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tutor com id " + id + " não encontrado"
                ));
    }
}
