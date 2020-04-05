package io.isotope.enigma.engine.aes;

import java.util.Arrays;

public class KeySpecification {

    private String name;
    private String key;
    private Integer iterations;
    private String salt;
    private byte[] iv;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "KeySpecification{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", iterations=" + iterations +
                ", salt='" + salt + '\'' +
                ", iv=" + Arrays.toString(iv) +
                '}';
    }
}
