package io.isotope.enigma.engine.config.properties;

import io.isotope.enigma.engine.services.aes.AESKeySpecification;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "enigma")
public class EnigmaProperties {

    @NestedConfigurationProperty
    private AESKeySpecification AESKeySpecification;

    private Boolean encryptDatabase = Boolean.FALSE;

    private Integer httpPort = 8081;

    public AESKeySpecification getKeySpecification() {
        return AESKeySpecification;
    }

    public void setKeySpecification(AESKeySpecification AESKeySpecification) {
        this.AESKeySpecification = AESKeySpecification;
    }

    public Boolean getEncryptDatabase() {
        return encryptDatabase;
    }

    public void setEncryptDatabase(Boolean encryptDatabase) {
        this.encryptDatabase = encryptDatabase;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }
}
