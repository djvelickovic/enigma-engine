package io.isotope.enigma.engine.aes;

public class Mocks {


    public static final Specification DEFAULT_SPEC = new Specification()
            .setCipher(new CipherSpecification()
                    .setIv(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                    .setMode(Mode.CBC)
                    .setPadding(Padding.PKCS5PADDING.getValue())
            )
            .setKey(new KeySpecification()
                    .setKey("test-key")
                    .setKeyLength(256)
                    .setIterations(65536)
                    .setSecretKeyFactory("PBKDF2WithHmacSHA256")
                    .setSalt("test-salt")
            );
}
