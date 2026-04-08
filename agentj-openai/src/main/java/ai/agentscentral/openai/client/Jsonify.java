package ai.agentscentral.openai.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

/**
 * Jsonify
 *
 * @author Rizwan Idrees
 */
class Jsonify {

    private static final JsonMapper mapper = JsonMapper.builder()
            .changeDefaultPropertyInclusion(incl -> incl
                    .withValueInclusion(JsonInclude.Include.NON_NULL)
                    .withContentInclusion(JsonInclude.Include.NON_NULL))
            .build();

    private Jsonify() {
    }

    static String asJson(Object o) throws JacksonException {
        return mapper.writeValueAsString(o);
    }

    static <T> T asObject(String stringValue, Class<T> clazz) throws JacksonException {
        return mapper.readValue(stringValue, clazz);
    }
}
