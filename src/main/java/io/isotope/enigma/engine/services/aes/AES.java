package io.isotope.enigma.engine.services.aes;

import java.security.SecureRandom;

public final class AES {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static final String NAME = "AES";
    public static final String BLOCK_MODE = "CBC";
    public static final String PADDING = "PKCS7Padding";
    public static final Integer AES_KEY_LENGTH = 256;
    public static final Integer BLOCK_SIZE = 128;

    private AES() { }

    public static KeySpecification generateKey() {
        KeySpecification keySpecification = new KeySpecification();

        byte[] key = new byte[AES_KEY_LENGTH / 8];
        byte[] iv = new byte[BLOCK_SIZE / 8];

        secureRandom.nextBytes(key);
        secureRandom.nextBytes(iv);

        keySpecification.setKey(key);
        keySpecification.setIv(iv);

        return keySpecification;
    }

    public static AESFactory key(KeySpecification keySpecification) {
        return new AESFactory(keySpecification);
    }
}
