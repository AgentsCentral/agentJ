package ai.agentscentral.core.tool;

import ai.agentscentral.core.annotation.InterruptParam;
import ai.agentscentral.core.annotation.ToolParam;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

/**
 * Utility class that creates typed {@link ToolParameter} and {@link InterruptParameter}
 * descriptors from reflected {@link java.lang.reflect.Parameter} instances.
 *
 * <p>Called by {@link ToolBagToolsExtractor} to select the most specific parameter
 * record based on the parameter's Java type. Two overloaded sets of factory methods are
 * provided — one set accepting a {@link ToolParam} annotation (for LLM-supplied
 * parameters) and a parallel set accepting an {@link InterruptParam} annotation (for
 * user-supplied interrupt parameters).</p>
 *
 * <p>This is a utility class and cannot be instantiated.</p>
 *
 * @author Rizwan Idrees
 */
public class ReflectionUtil {

    private ReflectionUtil() {
    }

    /**
     * Creates an {@link EnumToolParameter} for an enum-typed {@link ToolParam} parameter.
     *
     * @param index     zero-based parameter index
     * @param parameter the reflected parameter
     * @param toolParam the {@link ToolParam} annotation on the parameter
     * @return a fully populated {@link EnumToolParameter}
     */
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

    /**
     * Creates a {@link TypedToolParameter} for a primitive {@link ToolParam} parameter.
     *
     * @param index     zero-based parameter index
     * @param parameter the reflected parameter
     * @param toolParam the {@link ToolParam} annotation on the parameter
     * @return a fully populated {@link TypedToolParameter}
     */
    public static TypedToolParameter extractPrimitiveParameter(int index, Parameter parameter, ToolParam toolParam) {
        return new TypedToolParameter(index,
                parameter.getType(),
                toolParam.name(),
                toolParam.description(),
                toolParam.required(),
                parameter.getName()
        );
    }

    /**
     * Creates an {@link ArrayToolParameter} for an array-typed {@link ToolParam} parameter.
     *
     * @param index     zero-based parameter index
     * @param parameter the reflected parameter
     * @param toolParam the {@link ToolParam} annotation on the parameter
     * @return a fully populated {@link ArrayToolParameter}
     */
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

    /**
     * Creates a {@link CollectionToolParameter} for a {@link java.util.Collection}-typed
     * {@link ToolParam} parameter, resolving the generic element type.
     *
     * @param index     zero-based parameter index
     * @param parameter the reflected parameter
     * @param toolParam the {@link ToolParam} annotation on the parameter
     * @return a fully populated {@link CollectionToolParameter}
     */
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

    /**
     * Creates a {@link TypedToolParameter} for a general object {@link ToolParam} parameter.
     *
     * @param index     zero-based parameter index
     * @param parameter the reflected parameter
     * @param toolParam the {@link ToolParam} annotation on the parameter
     * @return a fully populated {@link TypedToolParameter}
     */
    public static TypedToolParameter extractTypedParameter(int index, Parameter parameter, ToolParam toolParam) {
        return new TypedToolParameter(index,
                parameter.getType(),
                toolParam.name(),
                toolParam.description(),
                toolParam.required(),
                parameter.getName()
        );
    }

    /**
     * Creates an {@link EnumInterruptParameter} for an enum-typed {@link InterruptParam} parameter.
     *
     * @param index          zero-based parameter index
     * @param parameter      the reflected parameter
     * @param interruptParam the {@link InterruptParam} annotation on the parameter
     * @return a fully populated {@link EnumInterruptParameter}
     */
    public static EnumInterruptParameter extractEnumParameter(int index,
                                                              Parameter parameter,
                                                              InterruptParam interruptParam) {
        try {
            return new EnumInterruptParameter(index,
                    parameter.getType(),
                    interruptParam.name(),
                    interruptParam.required(),
                    parameter.getName(),
                    enumValuesAsStrings(parameter.getType())

            );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a {@link TypedInterruptParameter} for a primitive {@link InterruptParam} parameter.
     *
     * @param index          zero-based parameter index
     * @param parameter      the reflected parameter
     * @param interruptParam the {@link InterruptParam} annotation on the parameter
     * @return a fully populated {@link TypedInterruptParameter}
     */
    public static TypedInterruptParameter extractPrimitiveParameter(int index,
                                                                    Parameter parameter,
                                                                    InterruptParam interruptParam) {
        return new TypedInterruptParameter(index,
                parameter.getType(),
                interruptParam.name(),
                interruptParam.required(),
                parameter.getName()
        );
    }


    /**
     * Creates an {@link ArrayInterruptParameter} for an array-typed {@link InterruptParam} parameter.
     *
     * @param index          zero-based parameter index
     * @param parameter      the reflected parameter
     * @param interruptParam the {@link InterruptParam} annotation on the parameter
     * @return a fully populated {@link ArrayInterruptParameter}
     */
    public static ArrayInterruptParameter extractArrayParameter(int index,
                                                                Parameter parameter,
                                                                InterruptParam interruptParam) {


        final Class<?> componentType = ofNullable(parameter.getType()).map(Class::getComponentType)
                .orElse(null);

        return new ArrayInterruptParameter(index,
                parameter.getType(),
                componentType,
                interruptParam.name(),
                interruptParam.required(),
                parameter.getName()
        );
    }

    /**
     * Creates a {@link CollectionInterruptParameter} for a {@link java.util.Collection}-typed
     * {@link InterruptParam} parameter, resolving the generic element type.
     *
     * @param index          zero-based parameter index
     * @param parameter      the reflected parameter
     * @param interruptParam the {@link InterruptParam} annotation on the parameter
     * @return a fully populated {@link CollectionInterruptParameter}
     */
    public static CollectionInterruptParameter extractCollectionParameter(int index,
                                                                          Parameter parameter,
                                                                          InterruptParam interruptParam) {

        final ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameterizedType();
        final Class<?> collectionType = (Class<?>) ofNullable(parameterizedType)
                .map(ParameterizedType::getActualTypeArguments)
                .filter(args -> args.length > 0)
                .map(args -> args[0]).orElse(null);

        return new CollectionInterruptParameter(index,
                parameter.getType(),
                collectionType,
                interruptParam.name(),
                interruptParam.required(),
                parameter.getName()
        );
    }

    /**
     * Creates a {@link TypedInterruptParameter} for a general object {@link InterruptParam} parameter.
     *
     * @param index          zero-based parameter index
     * @param parameter      the reflected parameter
     * @param interruptParam the {@link InterruptParam} annotation on the parameter
     * @return a fully populated {@link TypedInterruptParameter}
     */
    public static TypedInterruptParameter extractTypedParameter(int index,
                                                                Parameter parameter,
                                                                InterruptParam interruptParam) {
        return new TypedInterruptParameter(index,
                parameter.getType(),
                interruptParam.name(),
                interruptParam.required(),
                parameter.getName()
        );
    }

    private static <T> Set<String> enumValuesAsStrings(Class<T> enumType) throws ClassNotFoundException {
        return Stream.of(Class.forName(enumType.getCanonicalName()).getEnumConstants())
                .map(Object::toString).collect(Collectors.toSet());
    }


}
