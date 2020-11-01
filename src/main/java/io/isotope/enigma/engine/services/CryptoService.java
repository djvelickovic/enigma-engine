package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.aes.AES;
import io.isotope.enigma.engine.services.aes.AESKeySpecification;
import io.isotope.enigma.engine.services.crypto.StringDecryptor;
import io.isotope.enigma.engine.services.crypto.StringEncryptor;
import io.isotope.enigma.engine.services.exceptions.KeyNotFoundException;
import io.isotope.enigma.engine.services.rsa.RSA;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import static io.isotope.enigma.engine.services.KeyAssembler.*;

public class CryptoService {

    private final KeyRepository keyRepository;
    private final AESKeySpecification serviceKeySpecification;
    private final AES aes;

    public CryptoService(KeyRepository keyRepository, AESKeySpecification serviceKeySpecification, AES aes) {
        this.keyRepository = keyRepository;
        this.serviceKeySpecification = serviceKeySpecification;
        this.aes = aes;
    }

    public Map<String, String> encrypt(Map<String, String> values, String keyName) {
        StringEncryptor rsa = keyRepository.findPublicKey(keyName)
                .map(KeyAssembler::convert)
                .map(RSA::of)
                .map(rsaFactory -> rsaFactory.stringEncryptor(StandardCharsets.UTF_8))
                .orElseThrow(() -> new KeyNotFoundException("No key found with name " + keyName));

        return values.entrySet().stream()
                .map(e -> {
                    AESKeySpecification aesKey = aes.generateKey();

                    String encryptedValue = AES.of(aesKey)
                            .stringEncryptor(StandardCharsets.UTF_8)
                            .encrypt(e.getValue());

                    String b64AesKey = aesKeyB64(aesKey);
                    String b64AesEncrypted = rsa.encrypt(b64AesKey);
                    return Map.entry(e.getKey(), b64AesEncrypted+"#"+encryptedValue);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public String aesKeyB64(AESKeySpecification aesKey) {
        String key = bytesToString(b64encode(aesKey.getKey()));
        String iv = bytesToString(b64encode(aesKey.getIv()));
        return key + "." + iv;
    }

    public Map<String, String> decrypt(Map<String, String> values, String keyName) {
        StringDecryptor rsa = keyRepository.findPrivateKey(keyName)
                .map(key -> KeyAssembler.convert(key, serviceKeySpecification))
                .map(RSA::of)
                .map(rsaFactory -> rsaFactory.stringDecryptor(StandardCharsets.UTF_8))
                .orElseThrow(() -> new KeyNotFoundException("No key found with name " + keyName));

        return values.entrySet().stream()
                .map(e -> {
                    String[] split  = e.getValue().split("#");
                    String encAesKey = split[0];

                    String decAesKey = rsa.decrypt(encAesKey);
                    String[] aesParts = decAesKey.split("\\.");
                    byte[] key = b64decode(stringToBytes(aesParts[0]));
                    byte[] iv = b64decode(stringToBytes(aesParts[1]));

                    AESKeySpecification aesKey = AESKeySpecification.builder()
                            .key(key)
                            .iv(iv)
                            .build();

                    String decryptedValue = AES.of(aesKey)
                            .stringDecryptor(StandardCharsets.UTF_8)
                            .decrypt(split[1]);

                    return Map.entry(e.getKey(), decryptedValue);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
