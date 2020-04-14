package io.isotope.enigma.engine.controllers;

import io.isotope.enigma.engine.services.KeyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/external/keys", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ExternalKeyManagementController {

    private final KeyService keyService;

    public ExternalKeyManagementController(KeyService keyService) {
        this.keyService = keyService;
    }

    @GetMapping
    public ResponseEntity<?> getKeys() {
        return ResponseEntity.ok(keyService.getAllKeys());
    }

    @GetMapping("{key}")
    public ResponseEntity<?> getKey(@PathVariable String key) {
        return keyService.getKey(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createKey(@RequestBody KeySpecificationReduced keySpecification) {
        // validate
        keyService.addKey(keySpecification);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{key}")
    public ResponseEntity<?> deactivateKey(@PathVariable String key) {
        keyService.updateKey(key);
        return ResponseEntity.ok().build();
    }
}
