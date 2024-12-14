package configuration;

import com.demo.domain.entity.Count;
import com.demo.infrasctructure.repository.CountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.demo.infrasctructure.repository")
@EnableJpaRepositories(basePackages = {"com.demo.infrasctructure.repository"})
public class ConfigurationJpaTest {

    @Bean
    CommandLineRunner commandLineRunner(CountRepository cr) {
        return args -> {
            cr.save(Count.builder()
                    .id(1L)
                    .amount(1300)
                    .person("Andres")
                    .build());
        };
    }
}
