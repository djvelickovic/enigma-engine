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

import static io.isotope.enigma.engine.services.rsa.RSA.*;

public class RSAFactory {

    private final RSAKeySpecification specification;

    public RSAFactory(RSAKeySpecification specification) {
        this.specification = specification;
    }

    public StringDecryptor stringDecryptor(Charset charset) {
        return new StringDecryptor(decryptionCipher(), charset);
    }

    public StringEncryptor stringEncryptor(Charset charset) {
        return new StringEncryptor(encryptionCipher(), charset);
    }

    public MapStringDecryptor stringMapDecryptor(Charset charset) {
        return new MapStringDecryptor(stringDecryptor(charset));
    }

    public MapStringEncryptor stringMapEncryptor(Charset charset) {
        return new MapStringEncryptor(stringEncryptor(charset));
    }

    private Cipher encryptionCipher() {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(specification.getPublicKey());

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", NAME, BLOCK_MODE, PADDING));
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher;
        } catch (Exception e) {
            throw new RSAException("Error producing cipher", e);
        }
    }

    private Cipher decryptionCipher() {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(specification.getPrivateKey());

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", NAME, BLOCK_MODE, PADDING));
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher;
        } catch (Exception e) {
            throw new RSAException("Error producing cipher", e);
        }
    }
}
