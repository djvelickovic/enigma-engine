package io.isotope.enigma.engine.services.debug;

import io.isotope.enigma.engine.api.debug.DebugKeySpecification;
import io.isotope.enigma.engine.config.OnLocalProfile;
import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.db.DatabaseCrypto;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Conditional(OnLocalProfile.class)
public class DebugKeyService {

    private final KeyRepository keyRepository;
    private final DatabaseCrypto databaseCrypto;

    public DebugKeyService(KeyRepository keyRepository, DatabaseCrypto databaseCrypto) {
        this.keyRepository = keyRepository;
        this.databaseCrypto = databaseCrypto;
    }

    public Optional<DebugKeySpecification> getKey(String keyName) {
        return keyRepository.findByName(keyName)
                .map(databaseCrypto::decrypt)
                .map(key -> DebugKeySpecification.builder()
                        .id(key.getId())
                        .name(key.getName())
                        .padding(key.getPadding())
                        .blockCipherMode(key.getBlockCipherMode())
                        .active(key.getActive())
                        .created(key.getCreated())
                        .updated(key.getUpdated())
                        .privateKey(key.getPrivateKey())
                        .publicKey(key.getPublicKey())
                        .build()
                );
    }
}
