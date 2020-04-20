package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.config.properties.EnigmaProperties;
import io.isotope.enigma.engine.controllers.KeySpecificationReduced;
import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.aes.AES;
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

@Service
public class KeyService {

    private final KeyRepository keyRepository;
    private final RandomGenerator randomGenerator;
    private final DatabaseCrypto databaseCrypto;
    private final EnigmaProperties enigmaProperties;

    public KeyService(KeyRepository keyRepository, RandomGenerator randomGenerator, DatabaseCrypto databaseCrypto, EnigmaProperties enigmaProperties) {
        this.keyRepository = keyRepository;
        this.randomGenerator = randomGenerator;
        this.databaseCrypto = databaseCrypto;
        this.enigmaProperties = enigmaProperties;
    }

    public List<KeySpecificationReduced> getAllKeys() {
        return StreamSupport.stream(keyRepository.findAll().spliterator(), false)
                .map(KeyConverter::convertReduced)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addKey(KeySpecificationReduced key) {
        Key newKey = new Key();
        newKey.setId(UUID.randomUUID().toString());
        newKey.setName(key.getName());
        newKey.setActive(Boolean.TRUE);
        newKey.setCreated(LocalDateTime.now(ZoneId.of("UTC")));
        newKey.setUpdated(newKey.getCreated());
        newKey.setIterations(enigmaProperties.getIterations());
        newKey.setIv(generateIV());
        newKey.setSalt(salt());
        newKey.setKey(secretKey());
        databaseCrypto.encrypt(newKey);
        keyRepository.save(newKey);
    }


    private String generateIV() {
        byte[] iv = randomGenerator.generateRandomByteArray(AES.AES_INITIAL_VECTOR_LENGTH);
        return KeyConverter.convertIV(iv);
    }

    private String secretKey() {
        return randomGenerator.generateRandomString(enigmaProperties.getPrivateKeyLength());
    }

    private String salt() {
            return randomGenerator.generateRandomString(enigmaProperties.getSaltLength());
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
