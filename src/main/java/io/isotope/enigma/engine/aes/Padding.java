package io.isotope.enigma.engine.aes;

public enum Padding {

    PKCS5PADDING("PKCS5PADDING");

    private String value;

    private Padding(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
