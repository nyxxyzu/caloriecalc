package calcapp.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiError handleNotFoundException(final NotFoundException e) {
		return new ApiError("NOT_FOUND", e.getMessage(), "The required object was not found", LocalDateTime.now());
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiError handleAutomaticValidationException(final MethodArgumentNotValidException e) {
		return new ApiError("BAD_REQUEST", e.getMessage(), "Incorrectly made request", LocalDateTime.now());
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApiError handleConstraintViolationException(final ConstraintViolationException e) {
		return new ApiError("CONFLICT", e.getMessage(), "Integrity constraint has been violated", LocalDateTime.now());
	}
}
