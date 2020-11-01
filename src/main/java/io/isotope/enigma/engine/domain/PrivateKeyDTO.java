package io.isotope.enigma.engine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PrivateKeyDTO {

    private String name;
    private String privateKey;
    private String padding;
    private String blockCipherMode;
    private Integer size;

}

