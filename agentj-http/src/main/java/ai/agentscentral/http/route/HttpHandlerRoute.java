package ai.agentscentral.http.route;

import ai.agentscentral.http.handler.HttpHandler;
import ai.agentscentral.http.request.Request;

import java.util.Optional;

/**
 * HttpHandlerRoute
 *
 * @author Rizwan Idrees
 */
public class HttpHandlerRoute implements Route {

    private final String path;
    private final HttpMethod method;
    private final HttpHandler handler;
    private final PathPattern pathPattern;

    public HttpHandlerRoute(String path, HttpMethod method, HttpHandler handler) {
        this.path = path;
        this.method = method;
        this.handler = handler;
        this.pathPattern = PathPatternExtractor.extract(path);
    }


    @Override
    public Optional<HandlerMatchedRoute> match(Request request) {
        return Optional.of(request)
                .filter(r -> r.method() == method && pathPattern.pattern().matcher(r.path()).matches())
                .map(r -> new HandlerMatchedRoute(path, this));
    }
}
