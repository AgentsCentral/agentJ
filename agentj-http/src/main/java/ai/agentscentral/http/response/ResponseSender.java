package ai.agentscentral.http.response;

import jakarta.servlet.http.HttpServletResponse;

/**
 * ResponseSender
 *
 * @author Rizwan Idrees
 */
public interface ResponseSender {


    void send(HttpServletResponse response, MessageResponse messageResponse);

}
