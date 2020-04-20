package io.isotope.enigma.engine.controllers;

import io.isotope.enigma.engine.services.CryptoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(path = "/crypto/map")
@RestController
public class MapCryptoController {

    private static final Logger log = LoggerFactory.getLogger(MapCryptoController.class);

    private final CryptoService cryptoService;

    public MapCryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @PostMapping(path = "/encrypt/{key}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> encrypt(@PathVariable("key") String key, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok().body(cryptoService.encrypt(body, key));
    }

    @PostMapping(path = "/decrypt/{key}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> decrypt(@PathVariable("key") String key, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok().body(cryptoService.decrypt(body, key));
    }
}
