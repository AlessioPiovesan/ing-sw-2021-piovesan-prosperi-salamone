package it.polimi.ingsw.server;

import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.server.accessories.Json;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonTest {

    private final String SimpleTestCaseJsonSource = "{ \"title\": \"Code\", \"author\": \"nome\" }";

    @Test
    void parse() throws IOException {
        JsonNode node = Json.parse(SimpleTestCaseJsonSource);
        assertEquals("Code", node.get("title").asText());
    }

    @Test
    void fromJson() throws IOException {
        JsonNode node = Json.parse(SimpleTestCaseJsonSource);
        SimpleTestCaseJsonPOJO pojo = Json.fromJson(node, SimpleTestCaseJsonPOJO.class);
        assertEquals("Code", pojo.getTitle());
    }

    @Test
    void toJson() {
        SimpleTestCaseJsonPOJO pojo = new SimpleTestCaseJsonPOJO();
        pojo.setTitle("Testing 123");
        JsonNode node = Json.toJson(pojo);
        assertEquals("Testing 123", node.get("title").asText());
    }

    @Test
    void stringify() {
        SimpleTestCaseJsonPOJO pojo = new SimpleTestCaseJsonPOJO();
        pojo.setTitle("Testing 123");
        JsonNode node = Json.toJson(pojo);
        assertEquals("Testing 123", node.get("title").asText());
    }
}