package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;
import ai.agentscentral.http.route.convertors.ContentConvertor;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import static ai.agentscentral.http.route.PathPatternExtractor.extractPathVariables;
import static ai.agentscentral.http.route.convertors.TypeConvertors.*;
import static java.util.Comparator.comparingInt;

/**
 * HttpControllerRouter
 *
 * @author Rizwan Idrees
 */
public class HttpControllerRouter {


    private final ContentConvertor contentConvertor;


    public static final Object[] EMPTY_ARGUMENTS = new Object[]{};

    public HttpControllerRouter(ContentConvertor contentConvertor) {
        this.contentConvertor = contentConvertor;
    }

    public Response<?> route(ControllerMatchedRoute matchedRoute, Request request) {

        final ControllerMappedMatchedRoute mappedMatchedRoute = matchedRoute.mappedMatchedRoute();

        try {
            return (Response<?>) mappedMatchedRoute.method().invoke(matchedRoute.controller(),
                    methodArguments(mappedMatchedRoute, request));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e); //TODO:: default 500 response.
        }

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

        final String name = methodParameter.name();
        return switch (methodParameter.type()) {
            case PARAMETER -> parameterConvertor.convert(name, request, methodParameter.typeClass());
            case PATH -> pathConvertor.convert(name, pathNames, pathValues, methodParameter.typeClass());
            case HEADER -> headerConvertor.convert(name, request, methodParameter.typeClass());
            case BODY -> contentConvertor.convert(request, methodParameter.typeClass());
        };
    }

}
