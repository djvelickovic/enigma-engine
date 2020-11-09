package io.isotope.enigma.engine.services.rsa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.security.*;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@Service
public class RSA {

    private static final Logger logger = LoggerFactory.getLogger(RSA.class);
    private static final SecureRandom secureRandom = new SecureRandom();

    public static final String RSA = "RSA";
    public static final String BLOCK_MODE = "ECB";
    public static final String PADDING = "OAEPWithSHA-512AndMGF1Padding";
    public static final int RSA_4096 = 4096;

    public static RSAFactory of(RSAKeySpecification rsaKeySpecification) {
        return new RSAFactory(rsaKeySpecification);
    }

    private RSA() { }

    public static Optional<RSAKeySpecification> generateKey(Integer size) {
        try {
            StopWatch sw = new StopWatch();
            sw.start();

            int keySize = ofNullable(size).orElse(RSA_4096);

            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keySize, secureRandom);
            KeyPair keyPair = kpg.generateKeyPair();

            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            sw.stop();

            logger.info("RSA key generation finished in {} seconds ", sw.getTotalTimeSeconds());

            return Optional.of(RSAKeySpecification.builder()
                    .privateKey(privateKey.getEncoded())
                    .publicKey(publicKey.getEncoded())
                    .size(keySize)
                    .blockCipherMode(BLOCK_MODE)
                    .padding(PADDING)
                    .build());

        } catch (Exception e) {
            logger.error("Error generating RSA key", e);
            return empty();
        }
    }
}
