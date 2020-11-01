package io.isotope.enigma.engine.services;

import io.isotope.enigma.engine.api.RSAKeyMetadata;
import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.db.DatabaseCrypto;
import io.isotope.enigma.engine.services.exceptions.KeyNotFoundException;
import io.isotope.enigma.engine.services.exceptions.RSAException;
import io.isotope.enigma.engine.services.rsa.RSA;
import io.isotope.enigma.engine.services.rsa.RSAKeySpecification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.isotope.enigma.engine.services.KeyAssembler.b64encode;
import static io.isotope.enigma.engine.services.KeyAssembler.bytesToString;

@Service
public class KeyService {

    private final KeyRepository keyRepository;
    private final DatabaseCrypto databaseCrypto;
    private final RSA rsa;

    public KeyService(KeyRepository keyRepository, DatabaseCrypto databaseCrypto, RSA rsa) {
        this.keyRepository = keyRepository;
        this.databaseCrypto = databaseCrypto;
        this.rsa = rsa;
    }

    public List<RSAKeyMetadata> getAllKeys() {
        return StreamSupport.stream(keyRepository.findAll().spliterator(), false)
                .map(KeyAssembler::convertReduced)
                .collect(Collectors.toList());
    }

    @Transactional
    public void storeRSAKey(String keyName) {
        LocalDateTime created = LocalDateTime.now(ZoneId.of("UTC"));

        RSAKeySpecification generatedKey = rsa.generateKey()
                .orElseThrow(() -> new RSAException("Unable to generate rsa key"));

        Key key = Key.builder()
                .id(UUID.randomUUID().toString())
                .name(keyName)
                .active(Boolean.TRUE)
                .created(created)
                .updated(created)
                .size(generatedKey.getSize())
                .blockCipherMode(generatedKey.getBlockCipherMode())
                .padding(generatedKey.getPadding())
                .privateKey(bytesToString(b64encode(generatedKey.getPrivateKey())))
                .publicKey(bytesToString(b64encode(generatedKey.getPublicKey())))
                .build();

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
