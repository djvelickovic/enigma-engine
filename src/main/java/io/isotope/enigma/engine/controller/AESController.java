package io.isotope.enigma.engine.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AESController {

    private static final Logger log = LoggerFactory.getLogger(AESController.class);

    @PostMapping(path = "/encrypt/{key}")
    public ResponseEntity<?> encrypt(@PathVariable("key") String key, @RequestBody Map<String, String> body) {
        log.info(body.toString());
        return ResponseEntity.ok(body);
    }

    @PostMapping(path = "/decrypt/{key}")
    public ResponseEntity<?> decrypt(@PathVariable("key") String key, @RequestBody Map<String, String> body) {
        log.info(body.toString());
        return ResponseEntity.ok(body);
    }
}
