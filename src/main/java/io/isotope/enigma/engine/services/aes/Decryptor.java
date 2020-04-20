package io.isotope.enigma.engine.services.aes;

import io.isotope.enigma.engine.services.exceptions.EnigmaException;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.util.Base64;

public class Decryptor {

    private final Cipher cipher;

    protected Decryptor(Cipher cipher) {
        this.cipher = cipher;
    }

    public String decrypt(String value, Charset charset) {
        try {
            byte[] base64decoded = Base64.getDecoder().decode(value.getBytes(charset));
            byte[] encoded = cipher.doFinal(base64decoded);
            return new String(encoded, charset);
        } catch (Exception e) {
            throw new EnigmaException("Error while decrypting value", e);
        }
    }
}
