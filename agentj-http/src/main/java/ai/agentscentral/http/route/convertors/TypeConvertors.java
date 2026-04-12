package ai.agentscentral.http.route.convertors;

import ai.agentscentral.core.convertors.QuadConvertor;
import ai.agentscentral.core.convertors.TriConvertor;
import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.route.TypeConvertor;

import java.util.List;
import java.util.Map;

/**
 * Pre-built {@link ai.agentscentral.core.convertors.TriConvertor} and
 * {@link ai.agentscentral.core.convertors.QuadConvertor} constants for binding HTTP
 * request data to controller method parameters.
 *
 * <p>This is a utility class and cannot be instantiated.</p>
 *
 * @author Rizwan Idrees
 */
public class TypeConvertors {

    private TypeConvertors() {
    }

    /**
     * Reads a named query-string parameter from the request and converts it to the
     * specified target type using {@link TypeConvertor}.
     */
    public static final TriConvertor<String, Request, Class<?>, Object> parameterConvertor =
            (name, request, type) -> {
                final Map<String, String[]> parameters = request.parameters();
                return TypeConvertor.convert(parameters.get(name), type);
            };


    /**
     * Looks up a named path variable in the parallel lists of path variable names and
     * extracted values, then converts it to the specified target type.
     */
    public static final QuadConvertor<String, List<String>, List<String>, Class<?>, Object> pathConvertor =
            (name, pathNames, pathValues, type) -> {
                final String pathValue = pathValues.get(pathNames.indexOf(name));
                return TypeConvertor.convert(pathValue, type);
            };

    /**
     * Reads a named request header from the request and converts the first value to the
     * specified target type using {@link TypeConvertor}.
     */
    public static final TriConvertor<String, Request, Class<?>, Object> headerConvertor =
            (name, request, type) -> {
                final Map<String, List<String>> headers = request.headers();
                return TypeConvertor.convert(headers.get(name).toArray(String[]::new), type);
            };
}
