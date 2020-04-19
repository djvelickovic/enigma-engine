package io.isotope.enigma.engine.controllers.debug;

import java.time.LocalDateTime;

public class DebugKeySpecification {

    private String id;
    private String name;
    private String key;
    private Integer iterations;
    private String salt;
    private String iv;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getKey() {
        return key;
    }

    public DebugKeySpecification setKey(String key) {
        this.key = key;
        return this;
    }

    public Integer getIterations() {
        return iterations;
    }

    public DebugKeySpecification setIterations(Integer iterations) {
        this.iterations = iterations;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public DebugKeySpecification setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
