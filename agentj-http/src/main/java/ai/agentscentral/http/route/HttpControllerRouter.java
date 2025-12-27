package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import static ai.agentscentral.http.route.PathPatternExtractor.extractPathVariables;
import static java.util.Comparator.comparingInt;

/**
 * HttpControllerRouter
 *
 * @author Rizwan Idrees
 */
class HttpControllerRouter {


    public static final Object[] EMPTY_ARGUMENTS = new Object[]{};

    public Response route(ControllerMatchedRoute matchedRoute, Request request) {

        final ControllerMappedMatchedRoute mappedMatchedRoute = matchedRoute.mappedMatchedRoute();


        try {
            //todo map method parameters
            final Object invoked = mappedMatchedRoute.method().invoke(matchedRoute.controller());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e); //TODO:: default 500 response.
        }

        return null;
    }

    private Object[] methodArguments(ControllerMappedMatchedRoute mappedMatchedRoute,
                                     Request request) {

        final List<MethodParameter> methodParameters = mappedMatchedRoute.methodParameters();

        if (Objects.isNull(methodParameters) || methodParameters.isEmpty()) {
            return EMPTY_ARGUMENTS;
        }

        final PathPattern pathPattern = mappedMatchedRoute.pathPattern();

        final List<String> pathValues = extractPathVariables(pathPattern.pattern(),
                mappedMatchedRoute.path());

        methodParameters.sort(comparingInt(MethodParameter::index));

        return methodParameters.stream()
                .map(mp -> toArgument(mp, request, pathPattern.pathVariableNames(), pathValues))
                .toArray(Object[]::new);
    }

    private Object toArgument(MethodParameter methodParameter,
                              Request request,
                              List<String> pathNames,
                              List<String> pathValues) {

        //TODO:: do argument conversion
        return switch (methodParameter.type()) {
            case PARAMETER -> request.parameters().get(methodParameter.name());
            case PATH -> pathValue(methodParameter.name(), pathNames, pathValues);
            default -> null;
        };
    }

    private Object pathValue(String name, List<String> pathNames, List<String> pathValues) {
        return pathValues.get(pathNames.indexOf(name));
    }

}
