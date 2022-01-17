package intercorpretail.digital.reto.irdigitalbackend.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientWebDto implements Serializable {

	private static final long serialVersionUID = 6722469454135237166L;

	@JsonProperty("nombre")
	private String name;

	@JsonProperty("apellido")
	private String surnames;

	@JsonProperty("email")
	@NotNull
	@NotEmpty
	private String email;

	@JsonProperty("dni")
	@NotNull
	@NotEmpty
	private String dni;

	@JsonProperty("fechaCreacion")
	private Date creationDate;

	@JsonProperty("fechaNacimiento")
	@JsonFormat(pattern = "dd-MM-yyyy")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date birthDate;
}
