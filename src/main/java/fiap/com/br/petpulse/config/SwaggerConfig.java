package fiap.com.br.petpulse.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "PetPulse API",
                version = "1.0",
                description = "API REST para gerenciamento de tutores, pets, histórico clínico, dispositivos IoT e alertas inteligentes. O sistema funciona como uma carteira digital de saúde para pets, permitindo registrar informações, acompanhar dados clínicos e gerar alertas preventivos.",
                contact = @Contact(
                        name = "Equipe PetPulse - FIAP",
                        email = "contato@petpulse.com"
                ),
                license = @License(
                        name = "FIAP Challenge"
                )
        )
)
public class SwaggerConfig {
}
