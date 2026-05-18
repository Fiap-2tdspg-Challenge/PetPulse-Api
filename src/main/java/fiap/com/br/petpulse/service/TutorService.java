package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.TutorRequest;
import fiap.com.br.petpulse.dto.TutorResponse;
import fiap.com.br.petpulse.model.Tutor;
import fiap.com.br.petpulse.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public TutorResponse addTutor(TutorRequest request) {
        Tutor tutor = request.toEntity();
        return TutorResponse.toResponse(tutorRepository.save(tutor));
    }

    public Page<TutorResponse> getAllTutors(Pageable pageable) {
        return tutorRepository.findAll(pageable)
                .map(TutorResponse::toResponse);
    }

    public TutorResponse getTutorById(Long id) {
        return TutorResponse.toResponse(findTutorById(id));
    }

    public void deleteTutor(Long id) {
        findTutorById(id);
        tutorRepository.deleteById(id);
    }

    public TutorResponse updateTutor(Long id, TutorRequest request) {
        Tutor tutor = findTutorById(id);

        tutor.setName(request.name());
        tutor.setCpf(request.cpf());
        tutor.setEmail(request.email());
        tutor.setPassword(request.password());
        tutor.setPhone(request.phone());
        tutor.setAddress(request.address());

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
}
