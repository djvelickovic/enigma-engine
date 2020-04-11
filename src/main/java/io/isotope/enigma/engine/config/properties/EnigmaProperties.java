package io.isotope.enigma.engine.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "enigma")
public class EnigmaProperties {

    @NestedConfigurationProperty
    private KeyManagementProperties keyManagement;

    private Integer httpPort = 8081;

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }

    public KeyManagementProperties getKeyManagement() {
        return keyManagement;
    }

    public void setKeyManagement(KeyManagementProperties keyManagement) {
        this.keyManagement = keyManagement;
    }
}
