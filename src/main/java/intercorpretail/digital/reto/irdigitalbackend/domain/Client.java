package intercorpretail.digital.reto.irdigitalbackend.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("client")
public class Client implements Serializable {

	private static final long serialVersionUID = 5432395117889256846L;

	@Id
	private String id;

	private String name;

	private String surnames;

	private String email;

	@Indexed(unique = true)
	private String dni;

	@CreatedDate
	private Date creationDate;

	private Date birthDate;
}
