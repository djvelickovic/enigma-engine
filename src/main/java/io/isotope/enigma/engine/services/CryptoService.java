package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.aes.AES;
import io.isotope.enigma.engine.services.aes.AESKeySpecification;
import io.isotope.enigma.engine.services.exceptions.KeyNotFoundException;
import io.isotope.enigma.engine.services.rsa.RSA;
import io.isotope.enigma.engine.services.rsa.RSAKeySpecification;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

import static io.isotope.enigma.engine.services.KeyAssembler.*;

public class CryptoService {

    private final KeyRepository keyRepository;
    private final AESKeySpecification serviceKeySpecification;

    public CryptoService(KeyRepository keyRepository, AESKeySpecification serviceKeySpecification) {
        this.keyRepository = keyRepository;
        this.serviceKeySpecification = serviceKeySpecification;
    }

    public Map<String, String> encrypt(Map<String, String> values, String keyName) {
        RSAKeySpecification rsaKey = keyRepository.findPublicKey(keyName)
                .map(KeyAssembler::convert)
                .orElseThrow(() -> new KeyNotFoundException("No key found with name " + keyName));

        return values.entrySet().parallelStream()
                .map(e -> {
                    AESKeySpecification aesKey = AES.generateKey();

                    String encryptedValue = AES.of(aesKey)
                            .stringEncryptor(StandardCharsets.UTF_8)
                            .encrypt(e.getValue());

                    String b64AesKey = aesKeyB64(aesKey);
                    String b64AesEncrypted = RSA.of(rsaKey).stringEncryptor(StandardCharsets.UTF_8).encrypt(b64AesKey);
                    return Map.entry(e.getKey(), b64AesEncrypted+"#"+encryptedValue);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public String aesKeyB64(AESKeySpecification aesKey) {
        String key = bytesToString(b64encode(aesKey.getKey()));
        String iv = bytesToString(b64encode(aesKey.getIv()));
        return String.join(".", key, iv, aesKey.getBlockCipherMode(), aesKey.getPadding(), Integer.toString(aesKey.getSize()));
    }

    public Map<String, String> decrypt(Map<String, String> values, String keyName) {
        RSAKeySpecification rsaKey = keyRepository.findPrivateKey(keyName)
                .map(key -> KeyAssembler.convert(key, serviceKeySpecification))
                .orElseThrow(() -> new KeyNotFoundException("No key found with name " + keyName));

        return values.entrySet().parallelStream()
                .map(e -> {
                    String[] split  = e.getValue().split("#");
                    String encAesKey = split[0];

                    String decAesKey = RSA.of(rsaKey).stringDecryptor(StandardCharsets.UTF_8).decrypt(encAesKey);
                    String[] aesParts = decAesKey.split("\\.");
                    byte[] key = b64decode(stringToBytes(aesParts[0]));
                    byte[] iv = b64decode(stringToBytes(aesParts[1]));

                    AESKeySpecification aesKey = AESKeySpecification.builder()
                            .key(key)
                            .iv(iv)
                            .blockCipherMode(aesParts[2])
                            .padding(aesParts[3])
                            .size(Integer.parseInt(aesParts[4]))
                            .build();

                    String decryptedValue = AES.of(aesKey)
                            .stringDecryptor(StandardCharsets.UTF_8)
                            .decrypt(split[1]);

                    return Map.entry(e.getKey(), decryptedValue);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
