package configuration;

import com.demo.infrasctructure.repository.BankRepository;
import com.demo.infrasctructure.repository.CountRepository;
import com.demo.infrasctructure.service.CountService;
import com.demo.infrasctructure.service.CountServiceImp;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConfigurationUnitTest {

    @Bean
    public CountRepository countRepository() {
        return Mockito.mock(CountRepository.class);
    }

    @Bean
    public BankRepository bankRepository() {
        return Mockito.mock(BankRepository.class);
    }

    @Bean
    public CountService countService() {
        return new CountServiceImp(countRepository(),bankRepository());
    }
}
