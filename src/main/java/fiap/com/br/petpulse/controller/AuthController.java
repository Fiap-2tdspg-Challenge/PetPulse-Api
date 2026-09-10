package fiap.com.br.petpulse.controller;

import fiap.com.br.petpulse.service.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public record LoginRequest(

            @NotBlank(message = "O e-mail é obrigatório")
            @Email(message = "O e-mail deve ser válido")
            String email,

            @NotBlank(message = "A senha é obrigatória")
            String password

    ) {
    }

    public record TokenResponse(
            String token
    ) {
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest loginRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.email(),
                                loginRequest.password()
                        )
                );

        return new TokenResponse(
                tokenService.generateToken(authentication)
        );
    }
}