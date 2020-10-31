package io.isotope.enigma.engine.api.handlers;

import io.isotope.enigma.engine.api.ErrorResponse;
import io.isotope.enigma.engine.services.exceptions.KeyNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class KeyNotFoundExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {KeyNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleConflict(KeyNotFoundException e, WebRequest request) {
        return ResponseEntity.status(404)
                .body(new ErrorResponse()
                        .setCode("NOT_FOUND")
                        .setDescription(e.getMessage()));
    }
}