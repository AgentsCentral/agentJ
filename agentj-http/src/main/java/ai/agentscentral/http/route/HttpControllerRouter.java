package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static ai.agentscentral.http.route.PathPatternExtractor.extractPathVariables;

/**
 * HttpControllerRouter
 *
 * @author Rizwan Idrees
 */
class HttpControllerRouter {


    public Response route(ControllerMatchedRoute matchedRoute, Request request) {

        final ControllerMappedMatchedRoute mappedMatchedRoute = matchedRoute.mappedMatchedRoute();
        final PathPattern pathPattern = mappedMatchedRoute.pathPattern();
        final List<String> pathVariables = extractPathVariables(pathPattern.pattern(),
                mappedMatchedRoute.path());

        try {
            //todo map method parameters
            final Object invoked = mappedMatchedRoute.method().invoke(matchedRoute.controller());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e); //TODO:: default 500 response.
        }

        return null;
    }

}
