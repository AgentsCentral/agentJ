package ai.agentscentral.openai.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jsonify
 *
 * @author Rizwan Idrees
 */
class Jsonify {

    private static final ObjectMapper mapper = new ObjectMapper() {{
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }};

    private Jsonify() {
    }

    static String asJson(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    static <T> T asObject(String stringValue, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(stringValue, clazz);
    }
}
