package io.isotope.enigma.engine.config;

import io.isotope.enigma.engine.config.properties.EnigmaProperties;
import io.isotope.enigma.engine.repositories.KeyRepository;
import io.isotope.enigma.engine.services.CryptoService;
import io.isotope.enigma.engine.services.KeyService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({EnigmaProperties.class})
public class EnigmaConfiguration {

    @Bean
    public CryptoService cryptoService(KeyRepository keyRepository, EnigmaProperties enigmaProperties) {
        return new CryptoService(keyRepository, enigmaProperties.getKeySpecification());
    }

    @Bean
    public KeyService keyService(KeyRepository keyRepository, EnigmaProperties enigmaProperties) {
        return new KeyService(keyRepository, enigmaProperties.getKeySpecification());
    }
}
