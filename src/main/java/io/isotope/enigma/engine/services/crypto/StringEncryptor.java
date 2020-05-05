package io.isotope.enigma.engine.services.crypto;

import io.isotope.enigma.engine.services.exceptions.EngineException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Base64;

public class StringEncryptor implements Encryptor<String, String> {

    private final Engine engine;
    private final Charset charset;

    public StringEncryptor(Engine engine, Charset charset) {
        this.engine = engine;
        this.charset = charset;
    }

    @Override
    public String encrypt(String value) {

        try (InputStream is = new ByteArrayInputStream(value.getBytes(charset));
             ByteArrayOutputStream os = new ByteArrayOutputStream();
        ) {
            engine.process(is, os);
            byte[] b64encoded = Base64.getEncoder().encode(os.toByteArray());
            return new String(b64encoded, charset);
        } catch (Exception e) {
            throw new EngineException(e);
        }
    }
}
