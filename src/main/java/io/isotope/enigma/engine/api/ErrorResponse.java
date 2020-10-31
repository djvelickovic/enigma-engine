package io.isotope.enigma.engine.api;

public class ErrorResponse {
    private String code;
    private String description;

    public String getCode() {
        return code;
    }

    public ErrorResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ErrorResponse setDescription(String description) {
        this.description = description;
        return this;
    }
}
