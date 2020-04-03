package io.isotope.enigma.engine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {EngineApplication.class})
class CryptoTest {

	@LocalServerPort
	private Integer port;

	@Test
	void contextLoads() {

		JsonNode request = new ObjectMapper().createObjectNode().put("txt", "test");

		JsonNode response = WebClient.create("http://localhost:"+port+"/enigma/encrypt/somekey")
				.post()
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(JsonNode.class)
				.block();

		Assertions.assertEquals(request.get("txt").asText(), response.get("txt").asText());

	}

}
