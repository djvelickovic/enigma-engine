package io.isotope.enigma.engine.aes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;

public class Encoder {
    private static final Logger log = LoggerFactory.getLogger(Decoder.class);

    private final Cipher cipher;

    protected Encoder(Cipher cipher) {
        this.cipher = cipher;
    }

    public Optional<String> encode(String value, Charset charset) {
        try {
            byte[] encoded = cipher.doFinal(value.getBytes(charset));
            byte[] base64 = Base64.getEncoder().encode(encoded);
            return Optional.of(new String(base64, charset));
        } catch (Exception e) {
            log.error("Error while processing value",e);
            return Optional.empty();
        }
    }
}
