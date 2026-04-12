package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Package-private {@link Route} created by {@link ControllerMappedRoutesExtractor} for a
 * single HTTP-method annotation on a controller method.
 *
 * <p>At match time the request URI is tested against the compiled {@link PathPattern}.
 * A successful match produces a {@link ControllerMappedMatchedRoute} carrying the
 * reflected method and parameter metadata needed for invocation.</p>
 *
 * @author Rizwan Idrees
 */
class ControllerMappedRoute implements Route {

    private final String path;
    private final HttpMethod httpMethod;
    private final Method mappedMethod;
    private final List<MethodParameter> methodParameters;
    private final PathPattern pathPattern;

    ControllerMappedRoute(String path,
                          HttpMethod httpMethod,
                          Method mappedMethod,
                          List<MethodParameter> methodParameters) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.mappedMethod = mappedMethod;
        this.methodParameters = methodParameters;
        this.pathPattern = PathPatternExtractor.extract(path);
    }

    @Override
    public Optional<MatchedRoute> match(Request request) {

        final Predicate<Request> requestMatches = r -> r.method() == httpMethod &&
                pathPattern.pattern().matcher(r.path()).matches();

        return Optional.of(request)
                .filter(requestMatches)
                .map(r -> new ControllerMappedMatchedRoute(path, mappedMethod, pathPattern, methodParameters, this));
    }
}
