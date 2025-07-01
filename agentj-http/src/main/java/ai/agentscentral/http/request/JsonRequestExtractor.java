package ai.agentscentral.http.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * JsonRequestExtractor
 *
 * @author Rizwan Idrees
 */
public class JsonRequestExtractor implements RequestExtractor {

    public static final String POST = "POST";

    private final ObjectMapper objectMapper;

    public JsonRequestExtractor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public MessageRequest extract(HttpServletRequest request) {
        final String method = request.getMethod();

        if (!POST.equals(method)) {
            throw new RuntimeException("Method not supported");
        }

        try {
            return objectMapper.readValue(request.getInputStream(), MessageRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
