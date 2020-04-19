package io.isotope.enigma.engine.services.aes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.spec.KeySpec;
import java.util.Optional;

import static io.isotope.enigma.engine.services.aes.AES.*;

@Component
public class AESFactory {

    private static final Logger log = LoggerFactory.getLogger(AESFactory.class);

    private Optional<Key> createKeySpec(KeySpecification keySpecification) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY);
            KeySpec spec = new PBEKeySpec(keySpecification.getKey().toCharArray(), keySpecification.getSalt().getBytes(), keySpecification.getIterations(), AES_KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), AES);
            return Optional.of(secretKeySpec);
        } catch (Exception e) {
            log.error("Error while creating key", e);
            return Optional.empty();
        }
    }

    private Optional<Cipher> cipher(KeySpecification specification, int cipherMode) {
        byte[] iv = specification.getIv(); // 16
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        // TODO: validate fields

        try {
            Key secretKeySpec = createKeySpec(specification)
                    .orElseThrow(() -> new IllegalStateException("Unable to create key specification."));

            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", AES, BLOCK_MODE, PADDING));
            cipher.init(cipherMode, secretKeySpec, ivspec);

            return Optional.of(cipher);
        } catch (Exception e) {
            log.error("Error producing cipher", e);
            return Optional.empty();
        }
    }

    public Optional<Decoder> decoder(KeySpecification specification) {
        return cipher(specification, Cipher.DECRYPT_MODE)
                .map(Decoder::new);
    }

    public Optional<Encoder> encoder(KeySpecification specification) {
        return cipher(specification, Cipher.ENCRYPT_MODE)
                .map(Encoder::new);
    }
}