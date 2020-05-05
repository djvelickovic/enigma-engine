package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.controllers.KeySpecificationReduced;
import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.services.aes.KeySpecification;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyConverter {

    public static KeySpecification convert(Key key) {
        KeySpecification keySpecification = new KeySpecification();
        keySpecification.setKey(b64decode(stringToBytes(key.getKey())));
        keySpecification.setIv(b64decode(stringToBytes(key.getIv())));
        return keySpecification;
    }

    public static KeySpecificationReduced convertReduced(Key key) {
        KeySpecificationReduced keySpecification = new KeySpecificationReduced();
        keySpecification.setName(key.getName());
        keySpecification.setCreated(key.getCreated());
        keySpecification.setUpdated(key.getUpdated());
        keySpecification.setActive(key.getActive());
        return keySpecification;
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
