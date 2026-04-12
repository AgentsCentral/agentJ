package ai.agentscentral.core.tool;

/**
 * A {@link ToolParameter} for primitive and general object parameters.
 *
 * <p>Created by {@link ReflectionUtil#extractPrimitiveParameter} (for primitive types)
 * and {@link ReflectionUtil#extractTypedParameter} (for all other non-collection,
 * non-array, non-enum types).</p>
 *
 * @param index       zero-based position in the method's parameter list
 * @param type        the Java type of the parameter
 * @param name        the parameter name from {@link ai.agentscentral.core.annotation.ToolParam#name()}
 * @param description the parameter description from {@link ai.agentscentral.core.annotation.ToolParam#description()}
 * @param required    whether the model must supply a value
 * @param jvmName     the JVM parameter name ({@link java.lang.reflect.Parameter#getName()});
 *                    used for debugging and logging
 *
 * @author Rizwan Idrees
 */
public record TypedToolParameter(int index,
                                 Class<?> type,
                                 String name,
                                 String description,
                                 boolean required,
                                 String jvmName) implements ToolParameter {

}
