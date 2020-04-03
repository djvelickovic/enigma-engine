package io.isotope.enigma.engine.aes;

public class KeySpecification {

    private String key;
    private Integer keyLength;
    private Integer iterations;
    private String salt;
    private String secretKeyFactory;

    public String getKey() {
        return key;
    }

    public KeySpecification setKey(String key) {
        this.key = key;
        return this;
    }

    public Integer getKeyLength() {
        return keyLength;
    }

    public KeySpecification setKeyLength(Integer keyLength) {
        this.keyLength = keyLength;
        return this;
    }

    public Integer getIterations() {
        return iterations;
    }

    public KeySpecification setIterations(Integer iterations) {
        this.iterations = iterations;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public KeySpecification setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getSecretKeyFactory() {
        return secretKeyFactory;
    }

    public KeySpecification setSecretKeyFactory(String secretKeyFactory) {
        this.secretKeyFactory = secretKeyFactory;
        return this;
    }
}
