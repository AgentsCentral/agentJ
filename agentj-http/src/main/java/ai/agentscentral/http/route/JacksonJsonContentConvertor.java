package ai.agentscentral.http.route;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JacksonJsonContentConvertor
 *
 * @author Rizwan Idrees
 */
public class JacksonJsonContentConvertor implements ContentConvertor {

    private final ObjectMapper objectMapper;

    public JacksonJsonContentConvertor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public String serialize(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(String body, Class<T> clazz) {
        try {
            return objectMapper.readValue(body, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
