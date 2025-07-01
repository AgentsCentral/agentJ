package ai.agentscentral.http.request;

import jakarta.servlet.http.HttpServletRequest;

/**
 * RequestExtractor
 *
 * @author Rizwan Idrees
 */
public interface RequestExtractor {

    MessageRequest extract(HttpServletRequest request);
}
