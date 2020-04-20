package io.isotope.enigma.engine.services.aes;

import io.isotope.enigma.engine.services.exceptions.EnigmaException;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.util.Base64;

public class Encryptor {
    private final Cipher cipher;

    protected Encryptor(Cipher cipher) {
        this.cipher = cipher;
    }

    public String encrypt(String value, Charset charset) {
        try {
            byte[] encoded = cipher.doFinal(value.getBytes(charset));
            byte[] base64 = Base64.getEncoder().encode(encoded);
            return new String(base64, charset);
        } catch (Exception e) {
            throw new EnigmaException("Error while encrypting value", e);
        }
    }
}
