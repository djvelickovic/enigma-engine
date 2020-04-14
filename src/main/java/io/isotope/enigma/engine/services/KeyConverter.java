package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.controllers.KeySpecificationReduced;
import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.services.aes.KeySpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyConverter {

//    public static Key convert(KeySpecification keySpecification) {
//        Key key = new Key();
//        key.setKey(keySpecification.getKey());
//        key.setSalt(keySpecification.getSalt());
//        key.setIv(convertIV(keySpecification.getIv()));
//        key.setIterations(keySpecification.getIterations());
//        return key;
//    }

    public static KeySpecification convert(Key key) {
        KeySpecification keySpecification = new KeySpecification();
        keySpecification.setKey(key.getKey());
        keySpecification.setSalt(key.getSalt());
        keySpecification.setIv(convertIV(key.getIv()));
        keySpecification.setIterations(key.getIterations());
        return keySpecification;
    }

    public static KeySpecificationReduced convertReduced(Key key) {
        KeySpecificationReduced keySpecification = new KeySpecificationReduced();
        keySpecification.setName(key.getName());
        keySpecification.setCreated(key.getCreated());
        keySpecification.setActive(key.getActive());
        return keySpecification;
    }

    public static byte[] convertIV(String iv) {
        List<Byte> listBytes = Stream.of(iv.split(","))
                .map(String::trim)
                .map(Byte::valueOf)
                .collect(Collectors.toList());
        byte[] bytes = new byte[listBytes.size()];
        for (int i = 0; i < listBytes.size(); i++) {
            bytes[i] = listBytes.get(i);
        }
        return bytes;
    }

    public static String convertIV(byte[] iv) {
        List<String> value = new ArrayList<>();
        for (byte b : iv) {
            value.add(Byte.toString(b));
        }
        return String.join(",", value);
    }
}
