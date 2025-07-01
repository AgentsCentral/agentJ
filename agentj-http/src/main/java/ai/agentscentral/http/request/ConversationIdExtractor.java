package ai.agentscentral.http.request;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 * ConversationIdExtractor
 *
 * @author Rizwan Idrees
 */
public interface ConversationIdExtractor {


    Optional<String> extract(HttpServletRequest request);

}
