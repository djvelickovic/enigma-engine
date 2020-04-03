package io.isotope.enigma.engine.aes;

public class CipherSpecification {
    private String mode;
    private String padding;
    private byte[] iv;


    public String getMode() {
        return mode;
    }

    public CipherSpecification setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public String getPadding() {
        return padding;
    }

    public CipherSpecification setPadding(String padding) {
        this.padding = padding;
        return this;
    }

    public byte[] getIv() {
        return iv;
    }

    public CipherSpecification setIv(byte[] iv) {
        this.iv = iv;
        return this;
    }
}
