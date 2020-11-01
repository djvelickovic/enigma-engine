package io.isotope.enigma.engine.services.rsa;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RSAKeySpecification {
    private int size;
    private String padding;
    private String blockCipherMode;
    private byte[] privateKey;
    private byte[] publicKey;
}
