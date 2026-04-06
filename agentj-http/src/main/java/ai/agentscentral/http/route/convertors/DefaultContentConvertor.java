package ai.agentscentral.http.route.convertors;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * DefaultContentConvertor
 *
 * @author Rizwan Idrees
 */
public class DefaultContentConvertor implements ContentConvertor {

    private static final List<ContentTypeConvertor> DEFAULT_CONVERTORS =
            List.of(new JacksonJsonContentTypeConvertor(new ObjectMapper()));
    private static final String CONTENT_TYPE = "Content-Type";

    private final List<ContentTypeConvertor> contentTypeConvertors;

    public DefaultContentConvertor() {
        this(DEFAULT_CONVERTORS);
    }

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

