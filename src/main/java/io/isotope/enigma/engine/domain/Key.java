package io.isotope.enigma.engine.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "key")
public class Key {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "key_name")
    private String name;

    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "public_key")
    private String publicKey;

    @Column(name = "padding")
    private String padding;

    @Column(name = "block_cipher_mode")
    private String blockCipherMode;

    @Column(name = "size")
    private Integer size;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    @Column(name = "active")
    private Boolean active;
}
