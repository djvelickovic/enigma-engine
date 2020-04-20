package io.isotope.enigma.engine.services.db;

import io.isotope.enigma.engine.domain.Encrypted;
import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.services.aes.AESFactory;
import io.isotope.enigma.engine.services.aes.Decryptor;
import io.isotope.enigma.engine.services.aes.Encryptor;
import io.isotope.enigma.engine.services.aes.KeySpecification;
import io.isotope.enigma.engine.services.exceptions.EnigmaException;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

public class DatabaseCryptoService implements DatabaseCrypto {

    private final KeySpecification dbKeySpecification;
    private final AESFactory aesFactory;

    public DatabaseCryptoService(KeySpecification dbKeySpecification, AESFactory aesFactory) {
        this.dbKeySpecification = dbKeySpecification;
        this.aesFactory = aesFactory;
    }

    @Override
    public Key decrypt(Key key) {
        Decryptor dec = aesFactory.decryptor(dbKeySpecification);
        for (Field field : Key.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Encrypted.class)) {
                if (field.getType().isAssignableFrom(String.class)) {
                    try {
                        field.setAccessible(true);
                        String value = (String) field.get(key);
                        String decrypted = dec.decrypt(value, StandardCharsets.UTF_8);
                        field.set(key, decrypted);
                    } catch (Exception e) {
                        throw new EnigmaException(e);
                    } finally {
                        field.setAccessible(false);
                    }
                }
            }
        }
        return key;
    }

    @Override
    public Key encrypt(Key key) {
        Encryptor enc = aesFactory.encryptor(dbKeySpecification);

        for (Field field : Key.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Encrypted.class)) {
                if (field.getType().isAssignableFrom(String.class)) {
                    try {
                        field.setAccessible(true);
                        String value = (String) field.get(key);
                        String encrypted = enc.encrypt(value, StandardCharsets.UTF_8);
                        field.set(key, encrypted);
                    } catch (Exception e) {
                        throw new EnigmaException(e);
                    } finally {
                        field.setAccessible(false);
                    }
                }
            }
        }
        return key;
    }
}
