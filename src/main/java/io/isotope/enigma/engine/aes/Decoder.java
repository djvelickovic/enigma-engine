package io.isotope.enigma.engine.aes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;

public class Decoder {
    private static final Logger log = LoggerFactory.getLogger(Decoder.class);

    private Cipher cipher;

    protected Decoder(Cipher cipher) {
        this.cipher = cipher;
    }

    public Optional<String> decode(String value, Charset charset) {
        try {
            byte[] base64decoded = Base64.getDecoder().decode(value.getBytes(charset));
            byte[] encoded = cipher.doFinal(base64decoded);
            return Optional.of(new String(encoded, charset));
        } catch (Exception e) {
            log.error("Error while processing value",e);
            return Optional.empty();
        }
    }
}
