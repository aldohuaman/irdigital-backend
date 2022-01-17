package intercorpretail.digital.reto.irdigitalbackend.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListClientWebDto implements Serializable {

	private static final long serialVersionUID = -6899675260806977822L;

	@JsonProperty("clientes")
	private List<ClientWebDto> clientes;
}
