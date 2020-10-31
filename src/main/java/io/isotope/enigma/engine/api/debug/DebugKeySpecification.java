package io.isotope.enigma.engine.api.debug;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DebugKeySpecification {

    private String id;
    private String name;
    private String publicKey;
    private String privateKey;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Boolean active;

}
