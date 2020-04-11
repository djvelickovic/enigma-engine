package io.isotope.enigma.engine.config;

import io.isotope.enigma.engine.config.properties.EnigmaProperties;
import io.isotope.enigma.engine.gateway.KeyManagerGateway;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({EnigmaProperties.class})
public class EnigmaConfiguration {

    @Bean
    public KeyManagerGateway keyManagerGateway(EnigmaProperties enigmaProperties) {
        return new KeyManagerGateway(enigmaProperties.getKeyManagement().getUrl());
    }
}
