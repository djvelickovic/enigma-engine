package io.isotope.enigma.engine.controllers;

import io.isotope.enigma.engine.services.CryptoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(path = "/map", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class MapCryptoController {

    private static final Logger log = LoggerFactory.getLogger(MapCryptoController.class);

    private CryptoService cryptoService;

    public MapCryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @PostMapping(path = "/encrypt/{key}")
    public ResponseEntity<?> encrypt(@PathVariable("key") String key, @RequestBody Map<String, String> body) {
        log.info(body.toString());

        return cryptoService.encrypt(body, key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping(path = "/decrypt/{key}")
    public ResponseEntity<?> decrypt(@PathVariable("key") String key, @RequestBody Map<String, String> body) {
        log.info(body.toString());
        return cryptoService.decrypt(body, key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
