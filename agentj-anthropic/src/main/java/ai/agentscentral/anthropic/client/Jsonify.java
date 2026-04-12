package ai.agentscentral.anthropic.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

/**
 * Internal JSON serialisation/deserialisation utility for the Anthropic client.
 *
 * <p>Wraps a shared {@link tools.jackson.databind.json.JsonMapper} configured to omit
 * {@code null} values at both the property and collection-content level, matching the
 * Anthropic API's expectation that absent optional fields are not sent.</p>
 *
 * <p>This is a package-private utility class and cannot be instantiated.</p>
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
     * Serialises {@code o} to a JSON string, omitting {@code null} fields.
     *
     * @param o the object to serialise
     * @return the JSON representation
     * @throws JacksonException if serialisation fails
     */
    static String asJson(Object o) throws JacksonException {
        return mapper.writeValueAsString(o);
    }

    /**
     * Deserialises a JSON string into an instance of {@code clazz}.
     *
     * @param <T>         the target type
     * @param stringValue the JSON string to parse
     * @param clazz       the target class
     * @return the deserialised instance
     * @throws JacksonException if parsing or binding fails
     */
    static <T> T asObject(String stringValue, Class<T> clazz) throws JacksonException {
        return mapper.readValue(stringValue, clazz);
    }
}
