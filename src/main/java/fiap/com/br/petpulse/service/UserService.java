package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.model.User;
import fiap.com.br.petpulse.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Cacheable(value = "user")
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User addUser(User user){
        return userRepository.save(user);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User getUserById(Long id){
        return findUserById(id);
    }


    public void deleteUser(Long id) {
        findUserById(id);
        userRepository.deleteById(id);
    }


    public User updateUser(Long id, User newUser) {
        findUserById(id);
        newUser.setId(id);
        return userRepository.save(newUser);
    }


    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User com id " + id + " não encontrado")
        );
    }
}
