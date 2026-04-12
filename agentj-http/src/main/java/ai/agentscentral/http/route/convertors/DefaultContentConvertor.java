package ai.agentscentral.http.route.convertors;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Default {@link ContentConvertor} implementation that delegates to a list of
 * {@link ContentTypeConvertor}s, selecting the first one whose
 * {@link ContentTypeConvertor#matches(String)} returns {@code true} for the request or
 * response content-type.
 *
 * <p>When constructed with no arguments, a single {@link JacksonJsonContentTypeConvertor}
 * is pre-registered to handle {@code application/json} and vendor JSON types.</p>
 *
 * @author Rizwan Idrees
 */
public class DefaultContentConvertor implements ContentConvertor {

    private static final List<ContentTypeConvertor> DEFAULT_CONVERTORS =
            List.of(new JacksonJsonContentTypeConvertor(new ObjectMapper()));
    private static final String CONTENT_TYPE = "Content-Type";

    private final List<ContentTypeConvertor> contentTypeConvertors;

    /**
     * Creates a {@code DefaultContentConvertor} pre-configured with a
     * {@link JacksonJsonContentTypeConvertor} for {@code application/json}.
     */
    public DefaultContentConvertor() {
        this(DEFAULT_CONVERTORS);
    }

    /**
     * Creates a {@code DefaultContentConvertor} with a custom list of convertors.
     *
     * @param contentTypeConvertors ordered list of convertors; the first match wins
     */
    public DefaultContentConvertor(List<ContentTypeConvertor> contentTypeConvertors) {
        this.contentTypeConvertors = contentTypeConvertors;
    }

    @Override
    public <T> T convert(Request request, Class<T> clazz) {
        return findConvertor(request.getHeader(CONTENT_TYPE))
                .deserialize(request.body(), clazz);
    }

    @Override
    public <T> String convert(Response<T> response) {
        return findConvertor(response.contentType()).serialize(response.resource());
    }

    private ContentTypeConvertor findConvertor(String mediaType) {
        return contentTypeConvertors.stream().filter(c -> c.matches(mediaType))
                .findAny()
                .orElseThrow(() -> new RuntimeException("No convertor found for content-type " + mediaType));
    }

}

