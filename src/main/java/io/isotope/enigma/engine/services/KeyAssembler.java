package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.api.RSAKeyMetadata;
import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.services.rsa.RSAKeySpecification;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class KeyAssembler {

    public static RSAKeySpecification convert(Key key) {
        return RSAKeySpecification.builder()
                .publicKey(b64decode(stringToBytes(key.getPublicKey())))
                .privateKey(b64decode(stringToBytes(key.getPrivateKey())))
                .size(key.getSize())
                .build();
    }

    public static RSAKeyMetadata convertReduced(Key key) {
        return RSAKeyMetadata.builder()
                .name(key.getName())
                .created(key.getCreated())
                .updated(key.getUpdated())
                .active(key.getActive())
                .blockCipherMode(key.getBlockCipherMode())
                .padding(key.getPadding())
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
