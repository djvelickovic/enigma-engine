package io.isotope.enigma.engine.services.crypto;

public interface Encryptor<I, O> {
    O encrypt(I value);
}
