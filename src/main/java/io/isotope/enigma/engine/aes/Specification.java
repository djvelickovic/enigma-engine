package io.isotope.enigma.engine.aes;

public class Specification {
    private CipherSpecification cipher;
    private KeySpecification key;

    public CipherSpecification getCipher() {
        return cipher;
    }

    public Specification setCipher(CipherSpecification cipher) {
        this.cipher = cipher;
        return this;
    }

    public KeySpecification getKey() {
        return key;
    }

    public Specification setKey(KeySpecification key) {
        this.key = key;
        return this;
    }
}
