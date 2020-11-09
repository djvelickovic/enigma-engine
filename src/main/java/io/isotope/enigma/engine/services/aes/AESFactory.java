package io.isotope.enigma.engine.services.aes;

import io.isotope.enigma.engine.services.crypto.*;
import io.isotope.enigma.engine.services.exceptions.AESException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.Map;

import static io.isotope.enigma.engine.services.aes.AES.*;


public class AESFactory implements CryptoFactory {

    private final AESKeySpecification specification;

    public AESFactory(AESKeySpecification specification) {
        this.specification = specification;
    }

    @Override
    public Decryptor<String> stringDecryptor(Charset charset) {
        return new StringDecryptor(cipher(Cipher.DECRYPT_MODE), charset);
    }

    @Override
    public Encryptor<String> stringEncryptor(Charset charset) {
        return new StringEncryptor(cipher(Cipher.ENCRYPT_MODE), charset);
    }

    @Override
    public Decryptor<Map<String, String>> stringMapDecryptor(Charset charset) {
        return new MapStringDecryptor(stringDecryptor(charset));
    }

    @Override
    public Encryptor<Map<String, String>> stringMapEncryptor(Charset charset) {
        return new MapStringEncryptor(stringEncryptor(charset));
    }

    private Cipher cipher(int cipherMode) {
        if (specification.getIv() == null || specification.getIv().length != BLOCK_SIZE / 8) {
            throw new IllegalArgumentException("Initial vector length must be " + BLOCK_SIZE / 8);
        }

        if (specification.getKey() == null || specification.getKey().length != specification.getSize() / 8) {
            throw new IllegalArgumentException("Key size must be " + specification.getSize() / 8);
        }

        if (cipherMode != Cipher.DECRYPT_MODE && cipherMode != Cipher.ENCRYPT_MODE) {
            throw new IllegalArgumentException("Invalid cipher mode " + cipherMode);
        }

        try {
            IvParameterSpec ivspec = new IvParameterSpec(specification.getIv());
            Key secretKeySpec = new SecretKeySpec(specification.getKey(), AES);

            Cipher cipher = Cipher.getInstance(
                    String.format("%s/%s/%s",
                            AES,
                            specification.getBlockCipherMode(),
                            specification.getPadding()));

            cipher.init(cipherMode, secretKeySpec, ivspec);
            return cipher;
        } catch (Exception e) {
            throw new AESException("Error producing cipher", e);
        }
    }
}