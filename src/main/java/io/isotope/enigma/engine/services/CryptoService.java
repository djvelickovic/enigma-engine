package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.aes.AESFactory;
import io.isotope.enigma.engine.aes.Engine;
import io.isotope.enigma.engine.aes.Specification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    private static final Logger log = LoggerFactory.getLogger(CryptoService.class);

    private AESFactory aesFactory;

    public CryptoService(AESFactory aesFactory) {
        this.aesFactory = aesFactory;
    }

    private Optional<Map<String, String>> process(Engine engine, Map<String, String> values) {
        try {
            return Optional.of(values.entrySet().stream()
                    .map(e -> {
                        String cryptoValue = engine.process(e.getValue(), StandardCharsets.UTF_8)
                                .orElseThrow(() -> new IllegalStateException("error decrypting value " + e.getValue()));
                        return Map.entry(e.getKey(), cryptoValue);
                    })
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        } catch (Exception e) {
            log.error("Error processing values", e);
            return Optional.empty();
        }
    }

    public Optional<Map<String, String>> encrypt(Map<String, String> values) {
        return aesFactory.encryptor(new Specification())
                .flatMap(encryptor -> process(encryptor, values));
    }

    public Optional<Map<String, String>> decrypt(Map<String, String> values) {
        return aesFactory.decryptor(new Specification())
                .flatMap(decryptor -> process(decryptor, values));
    }
}
