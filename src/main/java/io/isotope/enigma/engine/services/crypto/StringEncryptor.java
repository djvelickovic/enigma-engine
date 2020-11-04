package io.isotope.enigma.engine.services.crypto;

import io.isotope.enigma.engine.services.exceptions.EngineException;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.util.Base64;

public class StringEncryptor implements Encryptor<String> {

    private final Cipher cipher;
    private final Charset charset;

    public StringEncryptor(Cipher cipher, Charset charset) {
        this.cipher = cipher;
        this.charset = charset;
    }

    @Override
    public String encrypt(String value) {
        try {
            byte[] processed = cipher.doFinal(value.getBytes(charset));
            byte[] b64encoded = Base64.getEncoder().encode(processed);
            return new String(b64encoded, charset);
        } catch (Exception e) {
            throw new EngineException(e);
        }
    }
}
