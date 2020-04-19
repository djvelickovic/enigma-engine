package io.isotope.enigma.engine.config;

import io.isotope.enigma.engine.config.properties.EnigmaProperties;
import io.isotope.enigma.engine.services.aes.AESFactory;
import io.isotope.enigma.engine.services.db.DatabaseCrypto;
import io.isotope.enigma.engine.services.db.DatabaseCryptoMock;
import io.isotope.enigma.engine.services.db.DatabaseCryptoService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({EnigmaProperties.class})
public class EnigmaConfiguration {

    @Bean
    public DatabaseCrypto databaseCrypto(AESFactory aesFactory, EnigmaProperties enigmaProperties) {
        if (!enigmaProperties.getEncryptDatabase()) {
            return new DatabaseCryptoMock();
        }
        return new DatabaseCryptoService(enigmaProperties.getKeySpecification(), aesFactory);
    }

}
