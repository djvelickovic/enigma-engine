package io.isotope.enigma.engine.gateway;

import io.isotope.enigma.engine.aes.KeySpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Optional;

@Service
public class KeyManagerGateway {

    private static final Logger log = LoggerFactory.getLogger(KeyManagerGateway.class);

    @Value("${external.key-management.url}")
    private String keyManagementUrl;

    public Optional<KeySpecification> getKeySpecification(String key) {
        log.debug("Fetching key {} specification", key);
        try {
            return WebClient.create(keyManagementUrl + "/keys/" + key)
                    .get()
                    .retrieve()
                    .bodyToMono(KeySpecification.class)
                    .blockOptional(Duration.ofSeconds(10))
                    .map(response -> {
                        log.info(response.toString());
                        return response;
                    });
        }
        catch (Exception e) {
            log.error("Error fetching key", e);
            return Optional.empty();
        }
    }

}
