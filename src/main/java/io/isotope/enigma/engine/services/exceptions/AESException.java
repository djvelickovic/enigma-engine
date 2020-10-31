package io.isotope.enigma.engine.services.exceptions;

public class AESException extends EnigmaException {

    public AESException() {
    }

    public AESException(String message) {
        super(message);
    }

    public AESException(String message, Throwable cause) {
        super(message, cause);
    }

    public AESException(Throwable cause) {
        super(cause);
    }
}
