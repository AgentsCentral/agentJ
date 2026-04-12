package ai.agentscentral.http.route.convertors;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.function.Predicate;

/**
 * {@link ContentTypeConvertor} that serialises and deserialises JSON using a Jackson
 * {@link ObjectMapper}.
 *
 * <p>By default, matches {@code application/json} and vendor JSON types of the form
 * {@code application/vnd.*+json}.  A custom {@link java.util.function.Predicate} can be
 * supplied via the two-argument constructor to override the matching logic.</p>
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

    /**
     * Creates a convertor with the default matcher ({@code application/json} and
     * {@code application/vnd.*+json}).
     *
     * @param objectMapper the Jackson mapper for serialisation and deserialisation
     */
    public JacksonJsonContentTypeConvertor(ObjectMapper objectMapper) {
        this(objectMapper, applicationJsonContent.or(vendorAPIJsonContent));
    }

    /**
     * Creates a convertor with a custom content-type matcher.
     *
     * @param objectMapper the Jackson mapper for serialisation and deserialisation
     * @param matcher      predicate that returns {@code true} for handled content-types
     */
    public JacksonJsonContentTypeConvertor(ObjectMapper objectMapper, Predicate<String> matcher) {
        this.objectMapper = objectMapper;
        this.matcher = matcher;
    }

    @Override
    public String serialize(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(String body, Class<T> clazz) {
        try {
            return objectMapper.readValue(body, clazz);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(String contentType) {
        return matcher.test(contentType);
    }

}
