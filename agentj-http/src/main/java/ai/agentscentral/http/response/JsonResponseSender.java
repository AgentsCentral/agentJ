package ai.agentscentral.http.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * JsonResponseSender
 *
 * @author Rizwan Idrees
 */
public class JsonResponseSender implements ResponseSender {

    private static final String CONTENT_TYPE = "application/json";
    private final ObjectMapper objectMapper;

    public JsonResponseSender(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void send(HttpServletResponse response, MessageResponse messageResponse) {

        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);

        try (PrintWriter writer = response.getWriter()) {

            objectMapper.writeValue(writer, messageResponse);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
