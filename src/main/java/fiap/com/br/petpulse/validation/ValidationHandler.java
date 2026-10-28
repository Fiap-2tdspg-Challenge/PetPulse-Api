package fiap.com.br.petpulse.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ValidationHandler {

    public record ValidationErrorResponse(String field, String message) {

        public ValidationErrorResponse(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationErrorResponse> handle(MethodArgumentNotValidException exception) {

        log.warn("Erro de validação: {}", exception.getMessage());

        return exception.getFieldErrors()
                .stream()
                .map(ValidationErrorResponse::new)
                .toList();
    }

    public record ErrorResponse(String message) {
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolation(
            DataIntegrityViolationException exception
    ) {

        log.warn("Erro de integridade de dados: {}", exception.getMessage());

        return new ErrorResponse(
                "Operação não permitida: o registro já existe ou possui dados relacionados."
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException exception) {
        log.warn("Erro de negócio: {} - {}", exception.getStatusCode(), exception.getReason());
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new ErrorResponse(exception.getReason()));
    }
}
