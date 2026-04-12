package ai.agentscentral.http.response;

import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

/**
 * {@link ResponseSender} implementation that serialises a {@link MessageResponse} to JSON
 * using a Jackson {@link ObjectMapper} and writes it as {@code application/json}.
 *
 * @author Rizwan Idrees
 */
public class JsonResponseSender implements ResponseSender {

    private static final String CONTENT_TYPE = "application/json";
    private final ObjectMapper objectMapper;

    /**
     * Creates a {@code JsonResponseSender} with the given {@link ObjectMapper}.
     *
     * @param objectMapper the mapper used to serialise the response body
     */
    public JsonResponseSender(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void send(HttpServletResponse response, MessageResponse messageResponse) {

        response.setContentType(CONTENT_TYPE);
        response.setStatus(SC_OK);

        try (PrintWriter writer = response.getWriter()) {

            objectMapper.writeValue(writer, messageResponse);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
