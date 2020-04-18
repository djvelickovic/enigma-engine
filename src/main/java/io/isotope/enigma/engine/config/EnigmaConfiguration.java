package io.isotope.enigma.engine.config;

import io.isotope.enigma.engine.config.properties.EnigmaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({EnigmaProperties.class})
public class EnigmaConfiguration {

}
