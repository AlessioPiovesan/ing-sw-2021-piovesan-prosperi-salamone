package it.polimi.ingsw.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json{
    private static ObjectMapper objectMapper = getObjectMapper();

    private static ObjectMapper getObjectMapper ( ){
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        /*configurazione*/
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return defaultObjectMapper;
    }

    public static JsonNode parse (String src) throws IOException{
        return objectMapper.readTree(src);
    }

    public static <A> A fromJson (JsonNode node, Class<A> clazz) throws JsonProcessingException{
        return objectMapper.treeToValue(node, clazz);
    }

    public static JsonNode toJson (Object a){
        return objectMapper.valueToTree(a);
    }

    public static String stringify (JsonNode node) throws JsonProcessingException{
        ObjectWriter objectWriter = objectMapper.writer();
        objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        return objectWriter.writeValueAsString(node);
    }
}
