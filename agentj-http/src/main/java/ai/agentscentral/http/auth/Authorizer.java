package ai.agentscentral.http.auth;

import ai.agentscentral.http.request.Request;

/**
 * Functional interface for authorising an incoming HTTP request.
 *
 * <p>One or more {@code Authorizer} instances can be registered in
 * {@link ai.agentscentral.http.config.HttpConfig}; the authorization filter evaluates
 * them in order and rejects the request with {@code 401 Unauthorized} if any returns
 * {@code false}.</p>
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface Authorizer {

    /**
     * Determines whether the given request is authorized.
     *
     * @param request the incoming HTTP request
     * @return {@code true} if the request is authorised; {@code false} to reject it
     */
    boolean isAuthorized(Request request);

}
