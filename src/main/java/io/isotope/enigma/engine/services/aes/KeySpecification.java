package io.isotope.enigma.engine.services.aes;

public class KeySpecification {

    private String key;
    private Integer iterations;
    private String salt;
    private byte[] iv;

    public String getKey() {
        return key;
    }

    public KeySpecification setKey(String key) {
        this.key = key;
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

    public byte[] getIv() {
        return iv;
    }

    public KeySpecification setIv(byte[] iv) {
        this.iv = iv;
        return this;
    }
}
