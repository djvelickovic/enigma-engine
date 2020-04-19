package io.isotope.enigma.engine.services.db;

import io.isotope.enigma.engine.domain.Encrypted;
import io.isotope.enigma.engine.domain.Key;
import io.isotope.enigma.engine.services.aes.AESFactory;
import io.isotope.enigma.engine.services.aes.KeySpecification;

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
        return aesFactory.decoder(dbKeySpecification)
                .map(dec -> {
                    for (Field field : Key.class.getDeclaredFields()){
                        if (field.isAnnotationPresent(Encrypted.class)) {
                            if (field.getType().isAssignableFrom(String.class)) {
                                try {
                                    field.setAccessible(true);
                                    String value = (String) field.get(key);
                                    String decrypted = dec.decode(value, StandardCharsets.UTF_8)
                                            .orElseThrow();
                                    field.set(key, decrypted);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                } finally {
                                    field.setAccessible(false);
                                }
                            }
                        }
                    }
                    return key;
                })
                .orElseThrow(()-> new RuntimeException("Unable to create db decrypt engine!"));
    }

    @Override
    public Key encrypt(Key key) {
        return aesFactory.encoder(dbKeySpecification)
                .map(enc -> {
                    for (Field field : Key.class.getDeclaredFields()){
                        if (field.isAnnotationPresent(Encrypted.class)) {
                            if (field.getType().isAssignableFrom(String.class)) {
                                try {
                                    field.setAccessible(true);
                                    String value = (String) field.get(key);
                                    String encrypted = enc.encode(value, StandardCharsets.UTF_8)
                                            .orElseThrow();

                                    field.set(key, encrypted);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                } finally {
                                    field.setAccessible(false);
                                }
                            }
                        }
                    }
                    return key;
                })
                .orElseThrow(()-> new RuntimeException("Unable to create db encrypt engine!"));
    }
}
