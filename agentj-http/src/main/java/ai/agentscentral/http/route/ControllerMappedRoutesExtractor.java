package ai.agentscentral.http.route;

import ai.agentscentral.http.route.annotations.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static ai.agentscentral.http.route.HttpMethod.*;
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
    public static final String FORWARD_SLASH = "/";


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
            return new ControllerMappedRoute(concatPath(rootPath, annotation.path()), DELETE, method);
        } else if (method.isAnnotationPresent(Get.class)) {
            final Get annotation = method.getAnnotation(Get.class);
            return new ControllerMappedRoute(concatPath(rootPath, annotation.path()), GET, method);
        } else if (method.isAnnotationPresent(Patch.class)) {
            final Patch annotation = method.getAnnotation(Patch.class);
            return new ControllerMappedRoute(concatPath(rootPath, annotation.path()), PATCH, method);
        } else if (method.isAnnotationPresent(Post.class)) {
            final Post annotation = method.getAnnotation(Post.class);
            return new ControllerMappedRoute(concatPath(rootPath, annotation.path()), POST, method);
        } else if (method.isAnnotationPresent(Put.class)) {
            final Put annotation = method.getAnnotation(Put.class);
            return new ControllerMappedRoute(concatPath(rootPath, annotation.path()), PUT, method);
        }

        throw new UnsupportedOperationException("Unknown request method ");
    }

    static String concatPath(String rootPath, String mappedPath) {
        final String safeRootPath = defaultString(rootPath);
        final String safeMappedPath = defaultString(mappedPath);

        if (safeRootPath.endsWith(FORWARD_SLASH) && safeMappedPath.startsWith(FORWARD_SLASH)) {
            return substringBeforeLast(safeRootPath, FORWARD_SLASH) + safeMappedPath;
        }

        return safeRootPath + safeMappedPath;
    }

}
