package io.isotope.enigma.engine.controllers.debug;

import io.isotope.enigma.engine.config.OnLocalProfile;
import io.isotope.enigma.engine.services.KeyService;
import io.isotope.enigma.engine.services.debug.DebugKeyService;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequestMapping(value = "/debug/keys", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Conditional(OnLocalProfile.class)
public class DebugKeyController {

    private final DebugKeyService debugKeyService;

    public DebugKeyController(DebugKeyService debugKeyService) {
        this.debugKeyService = debugKeyService;
    }

    @GetMapping("{key}")
    public ResponseEntity<?> getKey(@PathVariable String key) {
        return debugKeyService.getKey(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
