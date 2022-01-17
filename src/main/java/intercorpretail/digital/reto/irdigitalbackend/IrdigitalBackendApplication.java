package intercorpretail.digital.reto.irdigitalbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("intercorpretail.digital.reto.irdigitalbackend.repository")
public class IrdigitalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrdigitalBackendApplication.class, args);
	}

}
