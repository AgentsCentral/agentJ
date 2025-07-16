package ai.agentscentral.http.request;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 * SessionIdExtractor
 *
 * @author Rizwan Idrees
 */
public interface SessionIdExtractor {


    Optional<String> extract(HttpServletRequest request);

}
