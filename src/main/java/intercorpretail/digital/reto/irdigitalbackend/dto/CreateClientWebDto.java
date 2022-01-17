package intercorpretail.digital.reto.irdigitalbackend.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClientWebDto implements Serializable {

	private static final long serialVersionUID = 462270866769560835L;

	private String resultProcess;
	
	@JsonProperty("cliente")
	private ClientWebDto client;
}
