package io.isotope.enigma.engine.services.crypto;

import io.isotope.enigma.engine.services.exceptions.EngineException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Base64;

public class StringDecryptor implements Decryptor<String, String> {

    private final Engine engine;
    private final Charset charset;

    public StringDecryptor(Engine engine, Charset charset) {
        this.engine = engine;
        this.charset = charset;
    }

    @Override
    public String decrypt(String value) {
        try (InputStream is = new ByteArrayInputStream(Base64.getDecoder().decode(value.getBytes(charset)));
             ByteArrayOutputStream os = new ByteArrayOutputStream();
        ) {
            engine.process(is, os);
            return new String(os.toByteArray(), charset);
        } catch (Exception e) {
            throw new EngineException(e);
        }
    }
}
