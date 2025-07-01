package ai.agentscentral.http.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jsonify
 *
 * @author Rizwan Idrees
 */
public class Jsonify {

    private static final ObjectMapper mapper = new ObjectMapper();

    private Jsonify() {
    }

    public static String asJson(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    public static <T> T asObject(String stringValue, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(stringValue, clazz);
    }
}
