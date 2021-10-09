package mk.ukim.finki.sharedkernel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SharedKernelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharedKernelApplication.class, args);
    }

}
