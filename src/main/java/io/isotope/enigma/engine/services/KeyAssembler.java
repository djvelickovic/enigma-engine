package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.domain.KeyMetadataDTO;
import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.domain.PrivateKeyDTO;
import io.isotope.enigma.engine.domain.PublicKeyDTO;
import io.isotope.enigma.engine.services.aes.AES;
import io.isotope.enigma.engine.services.aes.AESKeySpecification;
import io.isotope.enigma.engine.services.rsa.RSAKeySpecification;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class KeyAssembler {

    public static RSAKeySpecification convert(PrivateKeyDTO key, AESKeySpecification aesKeySpecification) {
        String decryptedKey = AES.of(aesKeySpecification)
                .stringDecryptor(StandardCharsets.UTF_8)
                .decrypt(key.getPrivateKey());

        return RSAKeySpecification.builder()
                .privateKey(b64decode(stringToBytes(decryptedKey)))
                .size(key.getSize())
                .build();
    }

    public static RSAKeySpecification convert(PublicKeyDTO key) {
        return RSAKeySpecification.builder()
                .publicKey(b64decode(stringToBytes(key.getPublicKey())))
                .size(key.getSize())
                .build();
    }

    public static byte[] b64encode(byte[] value) {
        return Base64.getEncoder().encode(value);
    }

    public static byte[] b64decode(byte[] value) {
        return Base64.getDecoder().decode(value);
    }

    public static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] stringToBytes(String value) {
        return value.getBytes(StandardCharsets.UTF_8);
    }
}
