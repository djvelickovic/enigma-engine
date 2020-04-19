package io.isotope.enigma.engine.controllers;

import io.isotope.enigma.engine.services.KeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping(value = "/keys", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class KeyManagementController {

    private static final Logger logger = LoggerFactory.getLogger(KeyManagementController.class);

    private final KeyService keyService;

    public KeyManagementController(KeyService keyService) {
        this.keyService = keyService;
    }

    @GetMapping
    public ResponseEntity<?> getKeys() {
        logger.info("Fetching keys...");
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
        return validate(keySpecification.getName())
                .map(error -> ResponseEntity.badRequest().body(error))
                .orElseGet(() -> {
                    keyService.addKey(keySpecification);
                    return ResponseEntity.ok().build();
                });
    }

    @DeleteMapping("{key}")
    public ResponseEntity<?> deactivateKey(@PathVariable String key) {
        keyService.updateKey(key);
        return ResponseEntity.ok().build();
    }


    public Optional<ErrorResponse> validate(String keyName) {
        if (StringUtils.isEmpty(keyName)) {
            return Optional.of(new ErrorResponse()
                    .setCode("BAD_REQUEST")
                    .setDescription("Key cannot be empty."));
        }
        if (!Pattern.matches("([a-zA-Z0-9]+\\.*)+", keyName)) {
            return Optional.of(new ErrorResponse()
                    .setCode("BAD_REQUEST")
                    .setDescription("Invalid key pattern. Key name must contain only letters, numbers and dots."));
        }
        return Optional.empty();
    }
}
