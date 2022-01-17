package intercorpretail.digital.reto.irdigitalbackend.exceptions;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ClientExistsException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = -8928485303796634062L;
	
	public ClientExistsException() {
        super();
    }
	
	public ClientExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    public ClientExistsException(String message) {
        super(message);
    }
    public ClientExistsException(Throwable cause) {
        super(cause);
    }
}
