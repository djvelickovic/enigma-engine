package io.isotope.enigma.engine.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomGenerator {

    public static final SecureRandom SECURE_RANDOM = new SecureRandom();
    public static final String SEED = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890!@#$%^&*()_+=-[]'/.?<>";

    public byte[] generateRandomByteArray(int length) {
        byte[] iv = new byte[length];
        SECURE_RANDOM.nextBytes(iv);
        return iv;
    }

    public String generateRandomString(int length) {
        char[] generated = new char[length];
        for (int i = 0; i < length; i++) {
            generated[i] = SEED.charAt(SECURE_RANDOM.nextInt(SEED.length()));
        }
        return new String(generated);
    }

    public int randomInt(int bound) {
        return SECURE_RANDOM.nextInt(bound);
    }
}
