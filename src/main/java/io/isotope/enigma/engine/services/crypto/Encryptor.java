package io.isotope.enigma.engine.services.crypto;

public interface Encryptor<T> {
    T encrypt(T value);
}
