package ai.agentscentral.http.route.convertors;

import ai.agentscentral.core.convertors.QuadConvertor;
import ai.agentscentral.core.convertors.TriConvertor;
import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.route.TypeConvertor;

import java.util.List;
import java.util.Map;

public class TypeConvertors {


    private TypeConvertors() {
    }

    public static final TriConvertor<String, Request, Class<?>, Object> parameterConvertor =
            (name, request, type) -> {
                final Map<String, String[]> parameters = request.parameters();
                return TypeConvertor.convert(parameters.get(name), type);
            };


    public static final QuadConvertor<String, List<String>, List<String>, Class<?>, Object> pathConvertor =
            (name, pathNames, pathValues, type) -> {
                final String pathValue = pathValues.get(pathNames.indexOf(name));
                return TypeConvertor.convert(pathValue, type);
            };

    public static final TriConvertor<String, Request, Class<?>, Object> headerConvertor =
            (name, request, type) -> {
                final Map<String, List<String>> headers = request.headers();
                return TypeConvertor.convert(headers.get(name).toArray(String[]::new), type);
            };
}
