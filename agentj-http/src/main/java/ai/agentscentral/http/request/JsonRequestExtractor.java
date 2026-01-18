package ai.agentscentral.http.request;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
