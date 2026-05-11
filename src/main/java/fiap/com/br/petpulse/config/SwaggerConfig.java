package fiap.com.br.petpulse.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Petpulse API",
                description = "API desenvolvida para armazenar e organizar informações de pets, funcionando como uma carteira digital. Permite o cadastro de pets, controle de vacinas, exames, medicamentos, consultas, próximas ações de cuidado e geração de relatórios para o usuário.",
                version = "1.0"
        )
)
public class SwaggerConfig {
}
