package io.isotope.enigma.engine.services.crypto;

import io.isotope.enigma.engine.services.exceptions.EngineException;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.util.Base64;

public class StringDecryptor implements Decryptor<String> {

    private final Cipher cipher;
    private final Charset charset;

    public StringDecryptor(Cipher cipher, Charset charset) {
        this.cipher = cipher;
        this.charset = charset;
    }

    @Override
    public String decrypt(String value) {
        try {
            byte[] b64decoded = Base64.getDecoder().decode(value.getBytes(charset));
            byte[] bytes = cipher.doFinal(b64decoded);
            return new String(bytes, charset);
        } catch (Exception e) {
            throw new EngineException(e);
        }
    }
}
