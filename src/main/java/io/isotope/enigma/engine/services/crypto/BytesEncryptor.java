package io.isotope.enigma.engine.services.crypto;

import io.isotope.enigma.engine.services.exceptions.EngineException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Base64;

public class BytesEncryptor implements Encryptor<byte[], byte[]> {

    private final Engine engine;

    public BytesEncryptor(Engine engine) {
        this.engine = engine;
    }

    @Override
    public byte[] encrypt(byte[] value) {
        try (InputStream is = new ByteArrayInputStream(value);
             ByteArrayOutputStream os = new ByteArrayOutputStream();
        ) {
            engine.process(is, os);
            return os.toByteArray();
        } catch (Exception e) {
            throw new EngineException(e);
        }
    }
}
