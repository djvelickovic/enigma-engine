package io.isotope.enigma.engine.controller;

public class PlainText {
    private String plainText;

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    @Override
    public String toString() {
        return "PlainText{" +
                "plainText='" + plainText + '\'' +
                '}';
    }
}
