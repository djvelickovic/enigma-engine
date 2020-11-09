package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.domain.KeyMetadataDTO;
import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.aes.AES;
import io.isotope.enigma.engine.services.aes.AESKeySpecification;
import io.isotope.enigma.engine.services.exceptions.KeyNotFoundException;
import io.isotope.enigma.engine.services.exceptions.RSAException;
import io.isotope.enigma.engine.services.rsa.RSA;
import io.isotope.enigma.engine.services.rsa.RSAKeySpecification;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static io.isotope.enigma.engine.services.KeyAssembler.b64encode;
import static io.isotope.enigma.engine.services.KeyAssembler.bytesToString;

public class KeyService {

    private final KeyRepository keyRepository;
    private final AESKeySpecification serviceKeySpecification;

    public KeyService(KeyRepository keyRepository, AESKeySpecification serviceKeySpecification) {
        this.keyRepository = keyRepository;
        this.serviceKeySpecification = serviceKeySpecification;
    }

    public List<KeyMetadataDTO> getAllKeys() {
        return keyRepository.getAllKeysMetadata();
    }

    @Transactional
    public void createRSAKey(String keyName, Integer size) {
        LocalDateTime created = LocalDateTime.now(ZoneId.of("UTC"));

        RSAKeySpecification generatedKey = RSA.generateKey(size)
                .orElseThrow(() -> new RSAException("Unable to generate rsa key"));

        String privateKey = bytesToString(b64encode(generatedKey.getPrivateKey()));
        String encryptedPrivateKey = AES.of(serviceKeySpecification)
                .stringEncryptor(StandardCharsets.UTF_8)
                .encrypt(privateKey);

        Key key = Key.builder()
                .id(UUID.randomUUID().toString())
                .name(keyName)
                .active(Boolean.TRUE)
                .created(created)
                .updated(created)
                .size(generatedKey.getSize())
                .blockCipherMode(generatedKey.getBlockCipherMode())
                .padding(generatedKey.getPadding())
                .privateKey(encryptedPrivateKey)
                .publicKey(bytesToString(b64encode(generatedKey.getPublicKey())))
                .build();

        keyRepository.save(key);
    }

    @Transactional
    public void updateKey(String keyName, Boolean active) {
        keyRepository.findByName(keyName)
                .map(k -> {
                    k.setActive(active);
                    k.setUpdated(LocalDateTime.now(ZoneId.of("UTC")));
                    keyRepository.save(k);
                    return k;
                })
                .orElseThrow(() -> new KeyNotFoundException("Key does not exist " + keyName));
    }
}
