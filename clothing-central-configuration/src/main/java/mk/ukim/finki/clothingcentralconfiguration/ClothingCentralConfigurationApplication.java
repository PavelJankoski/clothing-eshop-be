package mk.ukim.finki.clothingcentralconfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ClothingCentralConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClothingCentralConfigurationApplication.class, args);
    }

}
