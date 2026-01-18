package ai.agentscentral.http.request;

import java.util.Optional;

/**
 * SessionIdExtractor
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface SessionIdExtractor {


    Optional<String> extract(Request request);

}
