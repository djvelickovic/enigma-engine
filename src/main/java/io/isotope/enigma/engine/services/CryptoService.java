package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.aes.AESFactory;
import io.isotope.enigma.engine.services.db.DatabaseCrypto;
import io.isotope.enigma.engine.services.exceptions.KeyNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    private final AESFactory aesFactory;
    private final KeyRepository keyRepository;
    private final DatabaseCrypto databaseCrypto;

    public CryptoService(AESFactory aesFactory, KeyRepository keyRepository, DatabaseCrypto databaseCrypto) {
        this.aesFactory = aesFactory;
        this.keyRepository = keyRepository;
        this.databaseCrypto = databaseCrypto;
    }

    private Map<String, String> process(BiFunction<String, Charset, String> engine, Map<String, String> values) {
        return values.entrySet().stream()
                .map(e -> {
                    String cryptoValue = engine.apply(e.getValue(), StandardCharsets.UTF_8);
                    return Map.entry(e.getKey(), cryptoValue);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, String> encrypt(Map<String, String> values, String keyName) {
        return keyRepository.findByNameAndActiveTrue(keyName)
                .map(databaseCrypto::decrypt)
                .map(KeyConverter::convert)
                .map(aesFactory::encryptor)
                .map(encryptor -> process(encryptor::encrypt, values))
                .orElseThrow(() -> new KeyNotFoundException("No key found with name " + keyName));

    }

    public Map<String, String> decrypt(Map<String, String> values, String keyName) {
        return keyRepository.findByNameAndActiveTrue(keyName)
                .map(databaseCrypto::decrypt)
                .map(KeyConverter::convert)
                .map(aesFactory::decryptor)
                .map(decryptor -> process(decryptor::decrypt, values))
                .orElseThrow(() -> new KeyNotFoundException("No key found with name " + keyName));
    }
}
