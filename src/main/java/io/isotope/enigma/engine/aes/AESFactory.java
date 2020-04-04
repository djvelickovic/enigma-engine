package io.isotope.enigma.engine.aes;

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

@Component
public class AESFactory {

    private static final Logger log = LoggerFactory.getLogger(AESFactory.class);

    private static final String AES = "AES";
    private static final Integer AES_KEY_LENGTH = 256;

    public Optional<Key> createKeySpec(KeySpecification keySpecification) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(keySpecification.getSecretKeyFactory());
            KeySpec spec = new PBEKeySpec(keySpecification.getKey().toCharArray(), keySpecification.getSalt().getBytes(), keySpecification.getIterations(), AES_KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), AES);
            return Optional.of(secretKeySpec);
        } catch (Exception e) {
            log.error("Error while creating key", e);
            return Optional.empty();
        }
    }

    private Optional<Cipher> cipher(Specification specification, int cipherMode) {
        String mode = specification.getCipher().getMode();
        String padding = specification.getCipher().getPadding();
        byte[] iv = specification.getCipher().getIv(); // 16
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        // TODO: validate fields

        try {
            Key secretKeySpec = createKeySpec(specification.getKey())
                    .orElseThrow(() -> new IllegalStateException("Unable to create key specification."));
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            Cipher cipher = Cipher.getInstance(String.format("AES/%s/%s", mode, padding));
            cipher.init(cipherMode, secretKeySpec, ivspec);

            return Optional.of(cipher);
        } catch (Exception e) {
            log.error("Error producing cipher", e);
            return Optional.empty();
        }
    }

    public Optional<Decoder> decoder(Specification specification) {
        return cipher(specification, Cipher.DECRYPT_MODE)
                .map(Decoder::new);
    }

    public Optional<Encoder> encoder(Specification specification) {
        return cipher(specification, Cipher.ENCRYPT_MODE)
                .map(Encoder::new);
    }
}