package io.isotope.enigma.engine.services.rsa;

import io.isotope.enigma.engine.services.crypto.*;
import io.isotope.enigma.engine.services.exceptions.RSAException;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

import static io.isotope.enigma.engine.services.rsa.RSA.*;

public class RSAFactory implements CryptoFactory {

    private final RSAKeySpecification specification;

    public RSAFactory(RSAKeySpecification specification) {
        this.specification = specification;
    }

    @Override
    public Decryptor<String> stringDecryptor(Charset charset) {
        return new StringDecryptor(decryptionCipher(), charset);
    }

    @Override
    public Encryptor<String> stringEncryptor(Charset charset) {
        return new StringEncryptor(encryptionCipher(), charset);
    }

    @Override
    public Decryptor<Map<String, String>> stringMapDecryptor(Charset charset) {
        return new MapStringDecryptor(stringDecryptor(charset));
    }

    @Override
    public Encryptor<Map<String, String>> stringMapEncryptor(Charset charset) {
        return new MapStringEncryptor(stringEncryptor(charset));
    }

    private Cipher encryptionCipher() {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(specification.getPublicKey());

            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", RSA, BLOCK_MODE, PADDING));
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher;
        } catch (Exception e) {
            throw new RSAException("Error producing cipher", e);
        }
    }

    private Cipher decryptionCipher() {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(specification.getPrivateKey());

            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", RSA, BLOCK_MODE, PADDING));
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher;
        } catch (Exception e) {
            throw new RSAException("Error producing cipher", e);
        }
    }
}
