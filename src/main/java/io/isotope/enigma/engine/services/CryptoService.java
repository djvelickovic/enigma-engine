package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.aes.AESFactory;
import io.isotope.enigma.engine.gateway.KeyManagerGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    private static final Logger log = LoggerFactory.getLogger(CryptoService.class);

    private AESFactory aesFactory;
    private KeyManagerGateway keyManagerGateway;


    public CryptoService(AESFactory aesFactory, KeyManagerGateway keyManagerGateway) {
        this.aesFactory = aesFactory;
        this.keyManagerGateway = keyManagerGateway;
    }

    private Optional<Map<String, String>> process(BiFunction<String, Charset, Optional<String>> engine, Map<String, String> values) {
        try {
            return Optional.of(
                    values.entrySet().stream()
                            .map(e -> {
                                String cryptoValue = engine.apply(e.getValue(), StandardCharsets.UTF_8)
                                        .orElseThrow(() -> new IllegalStateException("error decrypting value " + e.getValue()));
                                return Map.entry(e.getKey(), cryptoValue);
                            })
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        } catch (Exception e) {
            log.error("Error processing values", e);
            return Optional.empty();
        }
    }

    public Optional<Map<String, String>> encrypt(Map<String, String> values, String keyName) {
        return keyManagerGateway.getKeySpecification(keyName)
                .flatMap(keySpecification -> aesFactory.encoder(keySpecification)
                        .flatMap(encoder -> process(encoder::encode, values)));
    }

    public Optional<Map<String, String>> decrypt(Map<String, String> values, String keyName) {
        return keyManagerGateway.getKeySpecification(keyName)
                .flatMap(keySpecification -> aesFactory.decoder(keySpecification)
                        .flatMap(decoder -> process(decoder::decode, values)));
    }
}
