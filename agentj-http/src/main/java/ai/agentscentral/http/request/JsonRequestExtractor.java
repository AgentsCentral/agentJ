package ai.agentscentral.http.request;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

/**
 * {@link RequestExtractor} that deserializes the HTTP request body from JSON into a
 * {@link MessageRequest} using a Jackson {@link tools.jackson.databind.ObjectMapper}.
 *
 * @author Rizwan Idrees
 */
public class JsonRequestExtractor implements RequestExtractor {


    private final ObjectMapper objectMapper;

    /**
     * Creates a {@code JsonRequestExtractor} with the given {@link ObjectMapper}.
     *
     * @param objectMapper the mapper to use for JSON binding
     */
    public JsonRequestExtractor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public MessageRequest extract(Request request) {
        try {
            return objectMapper.readValue(request.body(), MessageRequest.class);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }

    }
}
