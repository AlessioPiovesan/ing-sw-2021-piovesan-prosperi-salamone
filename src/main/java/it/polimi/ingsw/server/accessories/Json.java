package it.polimi.ingsw.server.accessories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Json{

    private static final ObjectMapper objectMapper = getObjectMapper();

    private Json(){}

    private static ObjectMapper getObjectMapper(){
        var defaultObjectMapper = new ObjectMapper();
        /*configurazione*/
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return defaultObjectMapper;
    }

    public static JsonNode parse(String src) throws IOException{ return objectMapper.readTree(src); }

    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException{
        return objectMapper.treeToValue(node, clazz);
    }

    public static JsonNode toJson(Object a){
        return objectMapper.valueToTree(a);
    }

    public static String stringify(JsonNode node) throws JsonProcessingException{
        var objectWriter = objectMapper.writer();
        return objectWriter.writeValueAsString(node);
    }
}
