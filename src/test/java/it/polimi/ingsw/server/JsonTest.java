package it.polimi.ingsw.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonTest{

    private final String SimpleTestCaseJsonSource = "{ \"title\": \"Code\", \"author\": \"nome\" }";

    @Test
    void parse ( ) throws IOException{
        JsonNode node = Json.parse(SimpleTestCaseJsonSource);
        assertEquals(node.get("title").asText(), "Code");
    }

    @Test
    void fromJson ( ) throws IOException{
        JsonNode node = Json.parse(SimpleTestCaseJsonSource);
        SimpleTestCaseJsonPOJO pojo = Json.fromJson(node, SimpleTestCaseJsonPOJO.class);
        assertEquals(pojo.getTitle(), "Code");
    }

    @Test
    void toJson ( ){
        SimpleTestCaseJsonPOJO pojo = new SimpleTestCaseJsonPOJO();
        pojo.setTitle("Testing 123");
        JsonNode node = Json.toJson(pojo);
        assertEquals(node.get("title").asText(), "Testing 123");
    }

    @Test
    void stringify ( ) throws JsonProcessingException{
        SimpleTestCaseJsonPOJO pojo = new SimpleTestCaseJsonPOJO();
        pojo.setTitle("Testing 123");
        JsonNode node = Json.toJson(pojo);
        assertEquals(node.get("title").asText(), "Testing 123");
    }
}