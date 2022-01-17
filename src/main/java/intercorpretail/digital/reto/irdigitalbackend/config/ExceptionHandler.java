package intercorpretail.digital.reto.irdigitalbackend.config;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import intercorpretail.digital.reto.irdigitalbackend.exceptions.ClientExistsException;
import lombok.Getter;
import lombok.Setter;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(value = { ClientExistsException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		var genericException = (ClientExistsException) ex;
		var errorBody = new ErrorBody();
		errorBody.setMessage(genericException.getMessage());
		errorBody.setStatus(HttpStatus.CONFLICT.value());
		return handleExceptionInternal(genericException, errorBody, null, HttpStatus.CONFLICT, request);
	}
	
	
	@Getter
	@Setter
	public static class ErrorBody implements Serializable {

		private static final long serialVersionUID = -7212922978776708819L;
		
		private int status;
		private String message;
		
	}
}
