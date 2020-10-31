package io.isotope.enigma.engine.api;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KeyMetadata {

    private String name;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Boolean active;

}
