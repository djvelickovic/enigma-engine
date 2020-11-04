package io.isotope.enigma.engine.config.properties;

import io.isotope.enigma.engine.services.aes.AESKeySpecification;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "enigma")
public class EnigmaProperties {

    @NestedConfigurationProperty
    private AESKeySpecification keySpecification;

    private Integer httpPort = 8081;

    public AESKeySpecification getKeySpecification() {
        return keySpecification;
    }

    public void setKeySpecification(AESKeySpecification keySpecification) {
        this.keySpecification = keySpecification;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }
}
