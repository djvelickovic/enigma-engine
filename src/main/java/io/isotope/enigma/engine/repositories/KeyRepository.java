package io.isotope.enigma.engine.repositories;

import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.domain.KeyMetadataDTO;
import io.isotope.enigma.engine.domain.PrivateKeyDTO;
import io.isotope.enigma.engine.domain.PublicKeyDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface KeyRepository extends CrudRepository<Key, String> {

    Optional<Key> findByName(String keyName);

    @Query("select new io.isotope.enigma.engine.domain.KeyMetadataDTO(" +
            "k.name, " +
            "k.padding, " +
            "k.blockCipherMode, " +
            "k.size, " +
            "k.created, " +
            "k.updated, " +
            "k.active) " +
            "from Key k")
    List<KeyMetadataDTO> getAllKeysMetadata();

    @Query("select new io.isotope.enigma.engine.domain.PrivateKeyDTO(" +
            "k.name, " +
            "k.privateKey, " +
            "k.padding, " +
            "k.blockCipherMode, " +
            "k.size) " +
            "from Key k " +
            "where k.name = ?1 and k.active = true ")
    Optional<PrivateKeyDTO> findPrivateKey(String keyName);

    @Query("select new io.isotope.enigma.engine.domain.PublicKeyDTO(" +
            "k.name, " +
            "k.publicKey, " +
            "k.padding, " +
            "k.blockCipherMode, " +
            "k.size) " +
            "from Key k " +
            "where k.name = ?1 and k.active = true")
    Optional<PublicKeyDTO> findPublicKey(String keyName);
}
