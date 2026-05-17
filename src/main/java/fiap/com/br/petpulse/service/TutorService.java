package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.model.Tutor;
import fiap.com.br.petpulse.repositories.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public Tutor addUser(Tutor tutor){
        return tutorRepository.save(tutor);
    }

    public List<Tutor> getAllUsers() {
        return tutorRepository.findAll();
    }

    public Tutor getUserById(Long id){
        return findUserById(id);
    }

    public void deleteUser(Long id) {
        findUserById(id);
        tutorRepository.deleteById(id);
    }

    public Tutor updateUser(Long id, Tutor newTutor) {
        findUserById(id);
        newTutor.setId(id);
        return tutorRepository.save(newTutor);
    }

    private Tutor findUserById(Long id) {
        return tutorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tutor com id " + id + " não encontrado")
        );
    }
}
