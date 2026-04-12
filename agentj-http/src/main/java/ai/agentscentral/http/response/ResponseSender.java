package ai.agentscentral.http.response;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Strategy interface for writing a {@link MessageResponse} to an HTTP servlet response.
 *
 * <p>Implementations are responsible for setting the appropriate content-type and status
 * code before serialising the response body.</p>
 *
 * @author Rizwan Idrees
 */
public interface ResponseSender {

    /**
     * Serialises {@code messageResponse} and writes it to the servlet response.
     *
     * @param response        the Jakarta servlet response to write to
     * @param messageResponse the message payload to serialise and send
     */
    void send(HttpServletResponse response, MessageResponse messageResponse);

}
