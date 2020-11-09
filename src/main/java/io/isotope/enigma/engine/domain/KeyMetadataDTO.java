package io.isotope.enigma.engine.domain;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KeyMetadataDTO {

    private String name;
    private String padding;
    private String blockCipherMode;
    private Integer size;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Boolean active;

}
