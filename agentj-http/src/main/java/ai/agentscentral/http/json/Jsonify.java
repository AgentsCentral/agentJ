package ai.agentscentral.http.json;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

/**
 * Jsonify
 *
 * @author Rizwan Idrees
 */
public class Jsonify {

    private static final ObjectMapper mapper = new ObjectMapper();

    private Jsonify() {
    }

    public static String asJson(Object o) throws JacksonException {
        return mapper.writeValueAsString(o);
    }

    public static <T> T asObject(String stringValue, Class<T> clazz) throws JacksonException {
        return mapper.readValue(stringValue, clazz);
    }
}
