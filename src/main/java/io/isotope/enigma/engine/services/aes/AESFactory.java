package io.isotope.enigma.engine.services.aes;

import io.isotope.enigma.engine.services.exceptions.AesException;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.spec.KeySpec;

import static io.isotope.enigma.engine.services.aes.AES.AES;
import static io.isotope.enigma.engine.services.aes.AES.*;

@Service
public class AESFactory {

    private Key createKeySpec(KeySpecification keySpecification) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY);
            KeySpec spec = new PBEKeySpec(keySpecification.getKey().toCharArray(), keySpecification.getSalt().getBytes(), keySpecification.getIterations(), AES_KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            return new SecretKeySpec(tmp.getEncoded(), AES);
        } catch (Exception e) {
            throw new AesException("Error while creating key", e);
        }
    }

    private Cipher cipher(KeySpecification specification, int cipherMode) {
        if (specification.getIv() == null || specification.getIv().length != AES_INITIAL_VECTOR_LENGTH) {
            throw new IllegalArgumentException("Initial vector length must be "+AES_INITIAL_VECTOR_LENGTH);
        }
        if (cipherMode != Cipher.DECRYPT_MODE && cipherMode != Cipher.ENCRYPT_MODE) {
            throw new IllegalArgumentException("Invalid cipher mode "+cipherMode);
        }

        try {
            IvParameterSpec ivspec = new IvParameterSpec(specification.getIv());
            Key secretKeySpec = createKeySpec(specification);
            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", AES, BLOCK_MODE, PADDING));
            cipher.init(cipherMode, secretKeySpec, ivspec);
            return cipher;
        } catch (Exception e) {
            throw new AesException("Error producing cipher", e);
        }
    }

    public Decryptor decryptor(KeySpecification specification) {
        return new Decryptor(cipher(specification, Cipher.DECRYPT_MODE));
    }

    public Encryptor encryptor(KeySpecification specification) {
        return new Encryptor(cipher(specification, Cipher.ENCRYPT_MODE));
    }
}