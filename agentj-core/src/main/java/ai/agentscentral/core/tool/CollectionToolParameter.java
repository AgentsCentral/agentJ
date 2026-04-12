package ai.agentscentral.core.tool;

/**
 * A {@link ToolParameter} for {@link java.util.Collection}-typed parameters, capturing
 * the element type via generics reflection.
 *
 * <p>Created by {@link ReflectionUtil#extractCollectionParameter(int, java.lang.reflect.Parameter, ai.agentscentral.core.annotation.ToolParam)}
 * when the parameter type is assignable from {@link java.util.Collection}.</p>
 *
 * @param index       zero-based position in the method's parameter list
 * @param type        the raw collection type (e.g. {@code List.class})
 * @param genericType the element type resolved from the generic type argument; may be
 *                    {@code null} if the generic type cannot be determined
 * @param name        the parameter name from {@link ai.agentscentral.core.annotation.ToolParam#name()}
 * @param description the parameter description from {@link ai.agentscentral.core.annotation.ToolParam#description()}
 * @param required    whether the model must supply a value
 * @param jvmName     the JVM parameter name
 *
 * @author Rizwan Idrees
 */
public record CollectionToolParameter(int index,
                                      Class<?> type,
                                      Class<?> genericType,
                                      String name,
                                      String description,
                                      boolean required,
                                      String jvmName) implements ToolParameter {

}
