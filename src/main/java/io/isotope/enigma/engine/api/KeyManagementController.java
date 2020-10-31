package io.isotope.enigma.engine.api;

import io.isotope.enigma.engine.services.KeyService;
import io.isotope.enigma.engine.services.exceptions.EnigmaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(keyService.getAllKeys());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createKey(@RequestBody KeyMetadata keyMetadata) {

        if (StringUtils.isEmpty(keyMetadata.getName())) {
            throw new EnigmaException("Key cannot be empty");
        }
        if (!Pattern.matches("([a-zA-Z0-9]+\\.*)+", keyMetadata.getName())) {
            throw new EnigmaException("Invalid key pattern. Key name must contain only letters, numbers and dots.");
        }

        keyService.generateAndAddRSAKey(keyMetadata.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("{key}")
    public ResponseEntity<?> updateKey(@PathVariable String key, @RequestBody KeyMetadata keyMetadata) {
        keyService.updateKey(key, keyMetadata.getActive());
        return ResponseEntity.ok().build();
    }
}
