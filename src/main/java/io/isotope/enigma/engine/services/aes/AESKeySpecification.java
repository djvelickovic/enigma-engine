package io.isotope.enigma.engine.services.aes;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AESKeySpecification {

    private byte[] key;
    private byte[] iv;

}
