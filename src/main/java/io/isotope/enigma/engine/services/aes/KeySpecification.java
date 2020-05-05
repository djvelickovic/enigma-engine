package io.isotope.enigma.engine.services.aes;

public class KeySpecification {

    private byte[] key;
    private byte[] iv;

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getIv() {
        return iv;
    }

    public KeySpecification setIv(byte[] iv) {
        this.iv = iv;
        return this;
    }
}
