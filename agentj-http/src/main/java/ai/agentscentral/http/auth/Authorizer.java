package ai.agentscentral.http.auth;

import ai.agentscentral.http.request.Request;

/**
 * Authorizer
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface Authorizer {

    boolean isAuthorized(Request request);

}
