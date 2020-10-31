package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.api.KeyMetadata;
import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.db.DatabaseCrypto;
import io.isotope.enigma.engine.services.exceptions.KeyNotFoundException;
import io.isotope.enigma.engine.services.exceptions.RSAException;
import io.isotope.enigma.engine.services.rsa.RSA;
import io.isotope.enigma.engine.services.rsa.RSAKeySpecification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.isotope.enigma.engine.services.KeyConverter.*;

@Service
public class KeyService {

    private final KeyRepository keyRepository;
    private final DatabaseCrypto databaseCrypto;

    public KeyService(KeyRepository keyRepository, DatabaseCrypto databaseCrypto) {
        this.keyRepository = keyRepository;
        this.databaseCrypto = databaseCrypto;
    }

    public List<KeyMetadata> getAllKeys() {
        return StreamSupport.stream(keyRepository.findAll().spliterator(), false)
                .map(KeyConverter::convertReduced)
                .collect(Collectors.toList());
    }

    @Transactional
    public void generateAndAddRSAKey(String keyName) {
        Key key = new Key();
        key.setId(UUID.randomUUID().toString());
        key.setName(keyName);
        key.setActive(Boolean.TRUE);
        key.setCreated(LocalDateTime.now(ZoneId.of("UTC")));
        key.setUpdated(key.getCreated());


        RSAKeySpecification generatedKey = RSA.generateKey()
                .orElseThrow(() -> new RSAException("Unable to generate rsa key"));

        key.setPublicKey(bytesToString(b64encode(generatedKey.getPublicKey())));
        key.setPrivateKey(bytesToString(b64encode(generatedKey.getPrivateKey())));
        key.setSize(generatedKey.getSize());

        databaseCrypto.encrypt(key);
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
