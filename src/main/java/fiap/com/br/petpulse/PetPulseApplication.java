package fiap.com.br.petpulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@EnableCaching
@SpringBootApplication
public class PetPulseApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetPulseApplication.class, args);
    }

}
