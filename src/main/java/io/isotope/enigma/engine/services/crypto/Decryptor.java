package io.isotope.enigma.engine.services.crypto;

public interface Decryptor<I, O> {
    O decrypt(I value);
}
