package intercorpretail.digital.reto.irdigitalbackend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import intercorpretail.digital.reto.irdigitalbackend.domain.Client;

public interface ClientRepository extends MongoRepository<Client, String>{

	List<Client> findByEmail(@Param("email") String email);
	
	List<Client> findByDni(@Param("dni") String dni);
	
	Boolean existsByDniOrEmail(@Param("dni") String dni, @Param("email") String email);
}
