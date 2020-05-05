package io.isotope.enigma.engine.services.aes;

import io.isotope.enigma.engine.services.crypto.*;
import io.isotope.enigma.engine.services.exceptions.AesException;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;

import static io.isotope.enigma.engine.services.aes.AES.*;


public class AESFactory {

    public static AESFactory key(KeySpecification specification) {
        return new AESFactory(specification);
    }

    private final KeySpecification specification;

    private AESFactory(KeySpecification specification) {
        this.specification = specification;
    }

    public StringDecryptor stringDecryptor(Charset charset) {
        return new StringDecryptor(engine(Cipher.DECRYPT_MODE), charset);
    }

    public StringEncryptor stringEncryptor(Charset charset) {
        return new StringEncryptor(engine(Cipher.ENCRYPT_MODE), charset);
    }

    public MapStringDecryptor stringMapDecryptor(Charset charset) {
        return new MapStringDecryptor(stringDecryptor(charset));
    }

    public MapStringEncryptor stringMapEncryptor(Charset charset) {
        return new MapStringEncryptor(stringEncryptor(charset));
    }

    public BytesDecryptor bytesDecryptor() {
        return new BytesDecryptor(engine(Cipher.DECRYPT_MODE));
    }

    public BytesEncryptor bytesEncryptor() {
        return new BytesEncryptor(engine(Cipher.ENCRYPT_MODE));
    }


    private Cipher cipher(int cipherMode) {
        if (specification.getIv() == null || specification.getIv().length != BLOCK_SIZE / 8) {
            throw new IllegalArgumentException("Initial vector length must be " + BLOCK_SIZE / 8);
        }

        if (specification.getKey() == null || specification.getKey().length != AES_KEY_LENGTH / 8) {
            throw new IllegalArgumentException("Initial vector length must be " + AES_KEY_LENGTH / 8);
        }

        if (cipherMode != Cipher.DECRYPT_MODE && cipherMode != Cipher.ENCRYPT_MODE) {
            throw new IllegalArgumentException("Invalid cipher mode " + cipherMode);
        }

        try {
            IvParameterSpec ivspec = new IvParameterSpec(specification.getIv());
            Key secretKeySpec = new SecretKeySpec(specification.getKey(), AES.NAME);

            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", NAME, BLOCK_MODE, PADDING));
            cipher.init(cipherMode, secretKeySpec, ivspec);
            return cipher;
        } catch (Exception e) {
            throw new AesException("Error producing cipher", e);
        }
    }

    private Engine engine(int cipherMode) {
        return new Engine(cipher(cipherMode));
    }
}