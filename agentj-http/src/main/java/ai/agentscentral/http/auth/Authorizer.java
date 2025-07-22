package ai.agentscentral.http.auth;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Authorizer
 *
 * @author Rizwan Idrees
 */
public interface Authorizer {


    boolean isAuthorized(HttpServletRequest request);

}
