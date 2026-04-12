package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.HttpError;
import ai.agentscentral.http.response.Response;
import ai.agentscentral.http.route.convertors.ContentConvertor;

import java.util.List;
import java.util.Objects;

import static ai.agentscentral.http.route.PathPatternExtractor.extractPathVariables;
import static ai.agentscentral.http.route.convertors.TypeConvertors.*;
import static java.util.Comparator.comparingInt;

/**
 * Internal router that invokes the Java method associated with a
 * {@link ControllerMatchedRoute} by reflectively binding request parameters, path
 * variables, headers, and the request body to the method's parameters.
 *
 * <p>Parameter binding is determined by the annotation present on each method parameter:
 * <ul>
 *   <li>{@link ai.agentscentral.http.route.annotations.RequestParam} — from the query string</li>
 *   <li>{@link ai.agentscentral.http.route.annotations.PathVariable} — from the URI path</li>
 *   <li>{@link ai.agentscentral.http.route.annotations.Header} — from request headers</li>
 *   <li>{@link ai.agentscentral.http.route.annotations.Body} — deserialised from the
 *       request body using the configured {@link ContentConvertor}</li>
 * </ul>
 *
 * <p>If the controller method throws, a 500 response containing the exception message is
 * returned.</p>
 *
 * @author Rizwan Idrees
 */
public class HttpControllerRouter {


    private final ContentConvertor contentConvertor;

    /** Empty argument array used when a controller method has no parameters. */
    public static final Object[] EMPTY_ARGUMENTS = new Object[]{};

    /**
     * Creates an {@code HttpControllerRouter} with the given {@link ContentConvertor}.
     *
     * @param contentConvertor convertor used to deserialise request bodies and
     *                         serialise response bodies
     */
    public HttpControllerRouter(ContentConvertor contentConvertor) {
        this.contentConvertor = contentConvertor;
    }

    public Response<?> route(ControllerMatchedRoute matchedRoute, Request request) {

        final ControllerMappedMatchedRoute mappedMatchedRoute = matchedRoute.mappedMatchedRoute();

        try {
            return (Response<?>) mappedMatchedRoute.method().invoke(matchedRoute.controller(),
                    methodArguments(mappedMatchedRoute, request));
        } catch (Throwable e) {
            return Response.response(500, "application/json") // TODO:: introduce constants
                    .resource(new HttpError(e.getMessage()))
                    .build();
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
