package io.isotope.enigma.engine.services.exceptions;

public class RSAException extends EnigmaException {

    public RSAException() {
    }

    public RSAException(String message) {
        super(message);
    }

    public RSAException(String message, Throwable cause) {
        super(message, cause);
    }

    public RSAException(Throwable cause) {
        super(cause);
    }
}
