package io.isotope.enigma.engine.services.db;

import io.isotope.enigma.engine.domain.Key;

public interface DatabaseCrypto {
    Key decrypt(Key key);
    Key encrypt(Key key);
}

