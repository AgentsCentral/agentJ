package ai.agentscentral.http.handler;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;

/**
 * Functional interface for handling a single HTTP request and producing a typed
 * {@link Response}.
 *
 * <p>Implementations are registered with a {@link ai.agentscentral.http.route.HttpHandlerRoute}
 * and invoked by the {@link ai.agentscentral.http.route.HttpRouter} when a matching
 * request arrives.  The type parameter {@code T} is the type of the response body
 * resource, which is serialised to JSON (or another content type) by the
 * {@link ai.agentscentral.http.route.convertors.ContentConvertor}.</p>
 *
 * @param <T> the type of the response resource
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface HttpHandler<T> extends Handler {

    /**
     * Handles the given request and returns a {@link Response}.
     *
     * @param request the incoming HTTP request
     * @return a response carrying the HTTP status, content type, and optional body
     *         resource
     */
    Response<T> handle(Request request);

}
