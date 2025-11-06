package ai.agentscentral.http.auth;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Authorizer
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface Authorizer {

    boolean isAuthorized(HttpServletRequest request);

}
