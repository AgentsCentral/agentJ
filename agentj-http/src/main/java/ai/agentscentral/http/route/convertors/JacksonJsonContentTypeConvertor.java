package ai.agentscentral.http.route.convertors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Predicate;

/**
 * JacksonJsonContentConvertor
 *
 * @author Rizwan Idrees
 */
public class JacksonJsonContentTypeConvertor implements ContentTypeConvertor {

    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_VENDOR_PREFIX = "application/vnd";
    private static final String APPLICATION_VENDOR_SUFFIX = "+json";

    private static final Predicate<String> applicationJsonContent = contentType -> contentType.equals(APPLICATION_JSON);
    private static final Predicate<String> vendorAPIJsonContent = contentType ->
            contentType.startsWith(APPLICATION_VENDOR_PREFIX) && contentType.endsWith(APPLICATION_VENDOR_SUFFIX);

    private final ObjectMapper objectMapper;
    private final Predicate<String> matcher;

    public JacksonJsonContentTypeConvertor(ObjectMapper objectMapper) {
        this(objectMapper, applicationJsonContent.or(vendorAPIJsonContent));
    }

    public JacksonJsonContentTypeConvertor(ObjectMapper objectMapper, Predicate<String> matcher) {
        this.objectMapper = objectMapper;
        this.matcher = matcher;
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

    @Override
    public boolean matches(String contentType) {
        return matcher.test(contentType);
    }

}
