package ai.agentscentral.core.tool;

import ai.agentscentral.core.annotation.ToolParam;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

/**
 * ReflectionUtil
 *
 *
 * @author Rizwan Idrees
 */
public class ReflectionUtil {

    private ReflectionUtil() {
    }

    public static EnumToolParameter extractEnumParameter(int index, Parameter parameter, ToolParam toolParam) {
        try {
            return new EnumToolParameter(index,
                    parameter.getType(),
                    toolParam.name(),
                    toolParam.description(),
                    toolParam.required(),
                    parameter.getName(),
                    enumValuesAsStrings(parameter.getType())

            );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static TypedToolParameter extractPrimitiveParameter(int index, Parameter parameter, ToolParam toolParam) {
        return new TypedToolParameter(index,
                parameter.getType(),
                toolParam.name(),
                toolParam.description(),
                toolParam.required(),
                parameter.getName()
        );
    }

    public static ArrayToolParameter extractArrayParameter(int index, Parameter parameter, ToolParam toolParam) {

        final Class<?> componentType = ofNullable(parameter.getType()).map(Class::getComponentType)
                .orElse(null);

        return new ArrayToolParameter(index,
                parameter.getType(),
                componentType,
                toolParam.name(),
                toolParam.description(),
                toolParam.required(),
                parameter.getName()
        );
    }

    public static CollectionToolParameter extractCollectionParameter(int index, Parameter parameter, ToolParam toolParam) {

        final ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameterizedType();
        final Class<?> collectionType = (Class<?>) ofNullable(parameterizedType)
                .map(ParameterizedType::getActualTypeArguments)
                .filter(args -> args.length > 0)
                .map(args -> args[0]).orElse(null);

        return new CollectionToolParameter(index,
                parameter.getType(),
                collectionType,
                toolParam.name(),
                toolParam.description(),
                toolParam.required(),
                parameter.getName()
        );
    }

    public static TypedToolParameter extractTypedParameter(int index, Parameter parameter, ToolParam toolParam) {
        return new TypedToolParameter(index,
                parameter.getType(),
                toolParam.name(),
                toolParam.description(),
                toolParam.required(),
                parameter.getName()
        );
    }

    private static <T> Set<String> enumValuesAsStrings(Class<T> enumType) throws ClassNotFoundException {
        return Stream.of(Class.forName(enumType.getCanonicalName()).getEnumConstants())
                .map(Object::toString).collect(Collectors.toSet());
    }

}
