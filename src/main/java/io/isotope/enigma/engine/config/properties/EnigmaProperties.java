package io.isotope.enigma.engine.config.properties;

import io.isotope.enigma.engine.config.OnLocalProfile;
import io.isotope.enigma.engine.services.aes.KeySpecification;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Conditional;

@ConfigurationProperties(prefix = "enigma")
public class EnigmaProperties {

    @NestedConfigurationProperty
    private KeySpecification keySpecification;

    private Boolean encryptDatabase = Boolean.FALSE;

    private Integer iterations = 65535;
    private Integer privateKeyLength = 512;
    private Integer saltLength = 256;

    private Integer httpPort = 8081;

    public Integer getIterations() {
        return iterations;
    }

    public void setIterations(Integer iterations) {
        this.iterations = iterations;
    }

    public Integer getPrivateKeyLength() {
        return privateKeyLength;
    }

    public void setPrivateKeyLength(Integer privateKeyLength) {
        this.privateKeyLength = privateKeyLength;
    }

    public Integer getSaltLength() {
        return saltLength;
    }

    public void setSaltLength(Integer saltLength) {
        this.saltLength = saltLength;
    }

    public KeySpecification getKeySpecification() {
        return keySpecification;
    }

    public void setKeySpecification(KeySpecification keySpecification) {
        this.keySpecification = keySpecification;
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
