package io.isotope.enigma.engine.services.db;

import io.isotope.enigma.engine.domain.Key;

public class DatabaseCryptoMock implements DatabaseCrypto {
    @Override
    public Key decrypt(Key key) {
        return key;
    }

    @Override
    public Key encrypt(Key key) {
        return key;
    }
}
