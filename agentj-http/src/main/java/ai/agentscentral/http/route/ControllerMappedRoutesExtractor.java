package ai.agentscentral.http.route;

import ai.agentscentral.http.route.annotations.*;
import ai.agentscentral.http.route.annotations.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static ai.agentscentral.http.route.HttpMethod.*;
import static ai.agentscentral.http.route.ParameterType.PARAMETER;
import static ai.agentscentral.http.route.ParameterType.PATH;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

/**
 * ControllerMappedRoutesExtractor
 *
 * @author Rizwan Idrees
 */
class ControllerMappedRoutesExtractor {

    private static final Predicate<Method> requestMethods =
            method -> method.isAnnotationPresent(Delete.class) ||
                    method.isAnnotationPresent(Get.class) ||
                    method.isAnnotationPresent(Patch.class) ||
                    method.isAnnotationPresent(Post.class) ||
                    method.isAnnotationPresent(Put.class);

    private static final String FORWARD_SLASH = "/";


    private ControllerMappedRoutesExtractor() {
    }

    static List<ControllerMappedRoute> extract(String path, Object controller) {

        final Method[] declaredMethods = Optional.of(controller.getClass())
                .map(Class::getDeclaredMethods).orElseGet(() -> new Method[]{});

        return Stream.of(declaredMethods).filter(requestMethods)
                .map(m -> extractMappedRoute(path, m))
                .toList();
    }

    static ControllerMappedRoute extractMappedRoute(String rootPath, Method method) {

        if (method.isAnnotationPresent(Delete.class)) {
            final Delete annotation = method.getAnnotation(Delete.class);
            return mappedRoute(rootPath, annotation.path(), DELETE, method);
        } else if (method.isAnnotationPresent(Get.class)) {
            final Get annotation = method.getAnnotation(Get.class);
            return mappedRoute(rootPath, annotation.path(), GET, method);
        } else if (method.isAnnotationPresent(Patch.class)) {
            final Patch annotation = method.getAnnotation(Patch.class);
            return mappedRoute(rootPath, annotation.path(), PATCH, method);
        } else if (method.isAnnotationPresent(Post.class)) {
            final Post annotation = method.getAnnotation(Post.class);
            return mappedRoute(rootPath, annotation.path(), POST, method);
        } else if (method.isAnnotationPresent(Put.class)) {
            final Put annotation = method.getAnnotation(Put.class);
            return mappedRoute(rootPath, annotation.path(), PUT, method);
        }

        throw new UnsupportedOperationException("Unknown request method ");
    }

    private static ControllerMappedRoute mappedRoute(String rootPath,
                                                     String path,
                                                     HttpMethod method,
                                                     Method mappedMethod) {
        final String fullPath = concatPath(rootPath, path);
        final List<MethodParameter> methodParameters = extractMethodParameters(mappedMethod);
        return new ControllerMappedRoute(fullPath, method, mappedMethod, methodParameters);
    }

    private static String concatPath(String rootPath, String mappedPath) {
        final String safeRootPath = defaultString(rootPath);
        final String safeMappedPath = defaultString(mappedPath);

        if (safeRootPath.endsWith(FORWARD_SLASH) && safeMappedPath.startsWith(FORWARD_SLASH)) {
            return substringBeforeLast(safeRootPath, FORWARD_SLASH) + safeMappedPath;
        }

        return safeRootPath + safeMappedPath;
    }

    private static List<MethodParameter> extractMethodParameters(Method method) {

        final Parameter[] parameters = method.getParameters();

        if (isNull(parameters)) {
            return List.of();
        }

        return IntStream.range(0, parameters.length)
                .mapToObj(i -> extractMethodParameter(i, parameters[i]))
                .toList();
    }

    private static MethodParameter extractMethodParameter(int index, Parameter parameter) {
        if(parameter.isAnnotationPresent(RequestParam.class)){
            final RequestParam annotation = parameter.getAnnotation(RequestParam.class);
            return new MethodParameter(index, annotation.name(), annotation.required(), PARAMETER,
                    parameter.getType());
        }
        else if(parameter.isAnnotationPresent(PathVariable.class)){
            final PathVariable annotation = parameter.getAnnotation(PathVariable.class);
            return new MethodParameter(index, annotation.name(), true, PATH,
                    parameter.getType());
        }

        return null;
    }

}
