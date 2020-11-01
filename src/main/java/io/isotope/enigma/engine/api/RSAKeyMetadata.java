package io.isotope.enigma.engine.api;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RSAKeyMetadata {

    private String name;
    private String padding;
    private String blockCipherMode;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Boolean active;

}
