package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.controllers.KeySpecificationReduced;
import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.aes.AES;
import io.isotope.enigma.engine.services.aes.KeySpecification;
import io.isotope.enigma.engine.services.db.DatabaseCrypto;
import io.isotope.enigma.engine.services.exceptions.KeyNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public List<KeySpecificationReduced> getAllKeys() {
        return StreamSupport.stream(keyRepository.findAll().spliterator(), false)
                .map(KeyConverter::convertReduced)
                .collect(Collectors.toList());
    }

    @Transactional
    public void generateAndAddKey(String keyName) {
        Key key = new Key();
        key.setId(UUID.randomUUID().toString());
        key.setName(keyName);
        key.setActive(Boolean.TRUE);
        key.setCreated(LocalDateTime.now(ZoneId.of("UTC")));
        key.setUpdated(key.getCreated());

        KeySpecification generatedKey = AES.generateKey();
        key.setKey(bytesToString(b64encode(generatedKey.getKey())));
        key.setIv(bytesToString(b64encode(generatedKey.getIv())));

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
