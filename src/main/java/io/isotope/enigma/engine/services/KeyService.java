package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.config.properties.EnigmaProperties;
import io.isotope.enigma.engine.controllers.KeySpecificationReduced;
import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.aes.KeySpecification;
import io.isotope.enigma.engine.services.db.DatabaseCrypto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(KeyService.class);

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
        newKey.setIterations(iterations());
        newKey.setIv(generateIV());
        newKey.setSalt(salt());
        newKey.setKey(secretKey());
        databaseCrypto.encrypt(newKey);
        keyRepository.save(newKey);
    }


    private String generateIV() {
        byte[] iv = randomGenerator.generateRandomByteArray(16);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(iv[i]);
            sb.append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private String secretKey() {
        return randomGenerator.generateRandomString(512);
    }

    private String salt() {
            return randomGenerator.generateRandomString(256);
    }

    private int iterations() {
        return 65536;
    }

    @Transactional
    public void updateKey(String keyName) {
//        keyRepository.deleteById(keyName);
    }
}
