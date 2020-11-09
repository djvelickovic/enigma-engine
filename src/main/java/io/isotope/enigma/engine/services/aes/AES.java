package io.isotope.enigma.engine.services.aes;

import java.security.SecureRandom;

public final class AES {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static final String AES = "AES";
    public static final Integer BLOCK_SIZE = 128;

    private static final String BLOCK_MODE = "CBC";
    private static final String PADDING = "PKCS7Padding";
    private static final Integer AES_256 = 256;

    public static AESFactory of(AESKeySpecification AESKeySpecification) {
        return new AESFactory(AESKeySpecification);
    }

    private AES() { }

    public static AESKeySpecification generateKey() {

        byte[] key = new byte[AES_256 / 8];
        byte[] iv = new byte[BLOCK_SIZE / 8];

        secureRandom.nextBytes(key);
        secureRandom.nextBytes(iv);

        return AESKeySpecification.builder()
                .key(key)
                .iv(iv)
                .blockCipherMode(BLOCK_MODE)
                .padding(PADDING)
                .size(AES_256)
                .build();
    }
}
