package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.aes.AESFactory;
import io.isotope.enigma.engine.aes.Mocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    private static final Logger log = LoggerFactory.getLogger(CryptoService.class);

    private AESFactory aesFactory;

    public CryptoService(AESFactory aesFactory) {
        this.aesFactory = aesFactory;
    }

    private Optional<Map<String, String>> process(BiFunction<String, Charset, Optional<String>> engine, Map<String, String> values) {
        try {
            return Optional.of(values.entrySet().stream()
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
        return aesFactory.encoder(Mocks.DEFAULT_SPEC)
                .flatMap(encoder -> process(encoder::encode, values))
                .map(this::encodeToBase64);
    }

    public Optional<Map<String, String>> decrypt(Map<String, String> values, String keyName) {
        return decodeBase64(values)
                .flatMap(decodedValues -> aesFactory.decoder(Mocks.DEFAULT_SPEC)
                        .flatMap(decoder -> process(decoder::decode, decodedValues)));
    }

    private Optional<Map<String, String>> decodeBase64(Map<String, String> values) {
        try {
            Base64.Decoder enc = Base64.getDecoder();
            return Optional.of(
                    values.entrySet().stream()
                            .map(e -> Map.entry(e.getKey(), new String(enc.decode(e.getValue().getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8)))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        } catch (Exception e) {
            log.error("Error decoding base64", e);
            return Optional.empty();
        }
    }

    private Map<String, String> encodeToBase64(Map<String, String> values) {
        Base64.Encoder enc = Base64.getEncoder();
        return values.entrySet().stream()
                .map(e -> Map.entry(e.getKey(), new String(enc.encode(e.getValue().getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
