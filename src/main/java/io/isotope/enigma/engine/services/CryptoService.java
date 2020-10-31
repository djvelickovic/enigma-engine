package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.aes.AES;
import io.isotope.enigma.engine.services.aes.AESFactory;
import io.isotope.enigma.engine.services.db.DatabaseCrypto;
import io.isotope.enigma.engine.services.exceptions.KeyNotFoundException;
import io.isotope.enigma.engine.services.rsa.RSA;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    private final KeyRepository keyRepository;
    private final DatabaseCrypto databaseCrypto;

    public CryptoService(KeyRepository keyRepository, DatabaseCrypto databaseCrypto) {
        this.keyRepository = keyRepository;
        this.databaseCrypto = databaseCrypto;
    }

    public Map<String, String> encrypt(Map<String, String> values, String keyName) {
        return keyRepository.findByNameAndActiveTrue(keyName)
                .map(databaseCrypto::decrypt)
                .map(KeyConverter::convert)
                .map(RSA::key)
                .map(rsa -> rsa.stringMapEncryptor(StandardCharsets.UTF_8))
                .map(mapStringEncryptor -> mapStringEncryptor.encrypt(values))
                .orElseThrow(() -> new KeyNotFoundException("No key found with name " + keyName));
    }

    public Map<String, String> decrypt(Map<String, String> values, String keyName) {
        return keyRepository.findByNameAndActiveTrue(keyName)
                .map(databaseCrypto::decrypt)
                .map(KeyConverter::convert)
                .map(RSA::key)
                .map(rsa -> rsa.stringMapDecryptor(StandardCharsets.UTF_8))
                .map(mapStringEncryptor -> mapStringEncryptor.decrypt(values))
                .orElseThrow(() -> new KeyNotFoundException("No key found with name " + keyName));
    }
}
