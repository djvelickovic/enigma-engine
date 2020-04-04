package io.isotope.enigma.engine.aes;

public class Mocks {


    public static final KeySpecification DEFAULT_SPEC = new KeySpecification()
            .setIv(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
            .setKey("test-key")
            .setIterations(65536)
            .setSalt("test-salt");
}
