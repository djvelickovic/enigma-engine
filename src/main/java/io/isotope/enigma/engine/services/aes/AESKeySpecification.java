package io.isotope.enigma.engine.services.aes;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AESKeySpecification {

    private int size;
    private String padding;
    private String blockCipherMode;
    private byte[] key;
    private byte[] iv;

}
