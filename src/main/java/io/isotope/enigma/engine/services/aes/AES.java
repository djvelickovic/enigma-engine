package io.isotope.enigma.engine.services.aes;

public final class AES {

    public static final String AES = "AES";
    public static final String BLOCK_MODE = "CBC";
    public static final String PADDING = "PKCS5Padding";
    public static final String SECRET_KEY_FACTORY = "PBKDF2WithHmacSHA256";
    public static final Integer AES_KEY_LENGTH = 256;
    public static final Integer AES_INITIAL_VECTOR_LENGTH = 16;

    private AES() { }
}
