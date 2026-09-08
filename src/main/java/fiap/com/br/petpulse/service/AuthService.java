package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.enums.UserRole;
import fiap.com.br.petpulse.repositories.ProfessionalRepository;
import fiap.com.br.petpulse.repositories.TutorRepository;
import fiap.com.br.petpulse.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final TutorRepository tutorRepository;
    private final ProfessionalRepository professionalRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var tutor = tutorRepository.findByEmail(email);

        if (tutor.isPresent()) {
            var user = tutor.get();

            return new AuthenticatedUser(
                    user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    UserRole.ROLE_TUTOR
            );
        }

        var professional = professionalRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuário não encontrado com o email: " + email
                        )
                );

        return new AuthenticatedUser(
                professional.getId(),
                professional.getEmail(),
                professional.getPassword(),
                UserRole.ROLE_PROFESSIONAL
        );
    }
}