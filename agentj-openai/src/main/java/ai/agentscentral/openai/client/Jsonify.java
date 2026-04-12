package ai.agentscentral.openai.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

/**
 * Package-private JSON utility backed by a shared {@link tools.jackson.databind.json.JsonMapper}
 * configured to omit {@code null} values from serialised output.
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

    /**
     * Serialises the given object to a JSON string, omitting {@code null} fields.
     *
     * @param o the object to serialise
     * @return JSON representation
     * @throws tools.jackson.core.JacksonException if serialisation fails
     */
    static String asJson(Object o) throws JacksonException {
        return mapper.writeValueAsString(o);
    }

    /**
     * Deserialises the given JSON string into an instance of {@code clazz}.
     *
     * @param <T>         the target type
     * @param stringValue the JSON string to parse
     * @param clazz       the target class
     * @return the deserialised object
     * @throws tools.jackson.core.JacksonException if parsing fails
     */
    static <T> T asObject(String stringValue, Class<T> clazz) throws JacksonException {
        return mapper.readValue(stringValue, clazz);
    }
}
