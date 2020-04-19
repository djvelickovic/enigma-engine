package io.isotope.enigma.engine.repositories;

import io.isotope.enigma.engine.domain.Key;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface KeyRepository extends CrudRepository<Key, String> {

    Optional<Key> findByName(String keyName);
}
