package io.isotope.enigma.engine.aes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.util.Optional;

public class Engine {
    private static final Logger log = LoggerFactory.getLogger(Engine.class);

    private Cipher cipher;

    protected Engine(Cipher cipher) {
        this.cipher = cipher;
    }

    public Optional<String> process(String value, Charset charset) {
        try {
            return Optional.of(new String(cipher.doFinal(value.getBytes(charset)), charset));
        } catch (Exception e) {
            log.error("Error while processing value",e);
            return Optional.empty();
        }
    }
}
