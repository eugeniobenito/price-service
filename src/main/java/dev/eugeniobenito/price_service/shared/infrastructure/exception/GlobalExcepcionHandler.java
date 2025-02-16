package dev.eugeniobenito.price_service.shared.infrastructure.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dev.eugeniobenito.price_service.shared.domain.exception.ApplicationException;

@RestController
@ControllerAdvice
public class GlobalExcepcionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<CustomError> handle(ApplicationException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new CustomError(exception.getMessage()));
    }
}
