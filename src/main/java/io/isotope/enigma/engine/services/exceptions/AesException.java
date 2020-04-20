package io.isotope.enigma.engine.services.exceptions;

public class AesException extends EnigmaException {

    public AesException() {
    }

    public AesException(String message) {
        super(message);
    }

    public AesException(String message, Throwable cause) {
        super(message, cause);
    }

    public AesException(Throwable cause) {
        super(cause);
    }
}
