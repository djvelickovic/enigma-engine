package io.isotope.enigma.engine.services.crypto;

public interface Decryptor<T> {
    T decrypt(T value);
}
