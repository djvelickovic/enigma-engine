package io.isotope.enigma.engine.services.crypto;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MapStringDecryptor implements Decryptor<Map<String, String>, Map<String, String>> {

    private final StringDecryptor stringDecryptor;

    public MapStringDecryptor(StringDecryptor stringDecryptor) {
        this.stringDecryptor = stringDecryptor;
    }

    @Override
    public Map<String, String> decrypt(Map<String, String> value) {
        return value.entrySet().stream()
                .filter(v -> Objects.nonNull(v.getKey()))
                .filter(v -> Objects.nonNull(v.getValue()))
                .map(v -> Map.entry(v.getKey(), stringDecryptor.decrypt(v.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
