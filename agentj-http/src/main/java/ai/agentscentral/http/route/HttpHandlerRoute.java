package ai.agentscentral.http.route;

import ai.agentscentral.http.handler.HttpHandler;
import ai.agentscentral.http.request.Request;

import java.util.Optional;

/**
 * {@link Route} implementation that delegates matched requests to a
 * {@link ai.agentscentral.http.handler.HttpHandler}.
 *
 * <p>The route path is compiled into a {@link PathPattern} at construction time.  On
 * each call to {@link #match(Request)}, the request method and URI are checked against
 * the pattern; a successful match produces a {@link HandlerMatchedRoute} wrapping the
 * handler for dispatch by {@link HttpRouter}.</p>
 *
 * @param <T> the response body type produced by the handler
 *
 * @author Rizwan Idrees
 */
public class HttpHandlerRoute<T> implements Route {

    private final String path;
    private final HttpMethod method;
    private final HttpHandler<T> handler;
    private final PathPattern pathPattern;

    /**
     * Creates an {@code HttpHandlerRoute} for the given path, HTTP method, and handler.
     *
     * @param path    the route path template (may contain {@code {variable}} segments)
     * @param method  the HTTP method this route accepts
     * @param handler the handler to invoke when this route matches
     */
    public HttpHandlerRoute(String path, HttpMethod method, HttpHandler<T> handler) {
        this.path = path;
        this.method = method;
        this.handler = handler;
        this.pathPattern = PathPatternExtractor.extract(path);
    }


    @Override
    public Optional<MatchedRoute> match(Request request) {
        return Optional.of(request)
                .filter(r -> r.method() == method && pathPattern.pattern().matcher(r.path()).matches())
                .map(r -> new HandlerMatchedRoute(path, this, handler));
    }
}
