package ai.agentscentral.http.request;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

/**
 * JsonRequestExtractor
 *
 * @author Rizwan Idrees
 */
public class JsonRequestExtractor implements RequestExtractor {


    private final ObjectMapper objectMapper;

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
