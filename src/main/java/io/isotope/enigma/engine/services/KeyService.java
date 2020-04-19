package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.controllers.KeySpecificationReduced;
import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.aes.KeySpecification;
import io.isotope.enigma.engine.services.db.DatabaseCrypto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class KeyService {

    private final KeyRepository keyRepository;
    private final RandomGenerator randomGenerator;
    private final DatabaseCrypto databaseCrypto;

    public KeyService(KeyRepository keyRepository, RandomGenerator randomGenerator, DatabaseCrypto databaseCrypto) {
        this.keyRepository = keyRepository;
        this.randomGenerator = randomGenerator;
        this.databaseCrypto = databaseCrypto;
    }

    public List<KeySpecificationReduced> getAllKeys() {
        return StreamSupport.stream(keyRepository.findAll().spliterator(), false)
                .map(KeyConverter::convertReduced)
                .collect(Collectors.toList());
    }

    public Optional<KeySpecification> getKey(String keyName) {
        return keyRepository.findByName(keyName)
                .map(databaseCrypto::decrypt)
                .map(KeyConverter::convert);
    }

    @Transactional
    public void addKey(KeySpecificationReduced key) {
        Key newKey = new Key();
        newKey.setId(UUID.randomUUID().toString());
        newKey.setName(key.getName());
        newKey.setActive(Boolean.TRUE);
        newKey.setCreated(LocalDateTime.now(ZoneId.of("UTC")));
        newKey.setUpdated(newKey.getCreated());
        newKey.setIterations(iterations());
        newKey.setIv(generateIV());
        newKey.setSalt(salt());
        newKey.setKey(secretKey());
        databaseCrypto.encrypt(newKey);
        keyRepository.save(newKey);
    }


    public String generateIV() {
        byte[] iv = randomGenerator.generateRandomByteArray(16);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(iv[i]);
            sb.append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public String secretKey() {
        return randomGenerator.generateRandomString(512);
    }

    public String salt() {
        return randomGenerator.generateRandomString(256);
    }

    public int iterations() {
        return randomGenerator.randomInt(1024) + 65536;
    }

    @Transactional
    public void updateKey(String keyName) {
//        keyRepository.deleteById(keyName);
    }
}
