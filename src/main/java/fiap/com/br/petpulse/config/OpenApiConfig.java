package fiap.com.br.petpulse.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PetPulse API")
                        .version("1.0")
                        .description("API REST para gerenciamento de tutores, pets, histórico clínico, dispositivos IoT e alertas inteligentes. O sistema funciona como uma carteira digital de saúde para pets, permitindo registrar informações, acompanhar dados clínicos e gerar alertas preventivos.")
                        .contact(new Contact()
                                .name("Equipe PetPulse - FIAP")
                                .email("contato@petpulse.com"))
                        .license(new License().name("FIAP Challenge")))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
    }
}