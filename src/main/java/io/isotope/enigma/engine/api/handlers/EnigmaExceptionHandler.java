package io.isotope.enigma.engine.api.handlers;

import io.isotope.enigma.engine.api.ErrorResponse;
import io.isotope.enigma.engine.services.exceptions.EnigmaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EnigmaExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(EnigmaExceptionHandler.class);

    @ExceptionHandler(value = {EnigmaException.class})
    protected ResponseEntity<ErrorResponse> handleConflict(EnigmaException e, WebRequest request) {
        logger.error("Enigma error", e);
        return ResponseEntity.badRequest()
                .body(new ErrorResponse()
                        .setCode("BAD_REQUEST")
                        .setDescription(e.getMessage()));
    }
}