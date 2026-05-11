package fiap.com.br.petpulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PetPulseApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetPulseApplication.class, args);
    }

}
