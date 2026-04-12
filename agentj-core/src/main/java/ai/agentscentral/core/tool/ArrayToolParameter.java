package ai.agentscentral.core.tool;

/**
 * A {@link ToolParameter} for array-typed parameters, capturing the component type.
 *
 * <p>Created by {@link ReflectionUtil#extractArrayParameter(int, java.lang.reflect.Parameter, ai.agentscentral.core.annotation.ToolParam)}
 * when {@link Class#isArray()} returns {@code true} for the parameter type.</p>
 *
 * @param index       zero-based position in the method's parameter list
 * @param type        the array type (e.g. {@code String[].class})
 * @param arrayType   the component type of the array (e.g. {@code String.class}); may be
 *                    {@code null} if it cannot be determined
 * @param name        the parameter name from {@link ai.agentscentral.core.annotation.ToolParam#name()}
 * @param description the parameter description from {@link ai.agentscentral.core.annotation.ToolParam#description()}
 * @param required    whether the model must supply a value
 * @param jvmName     the JVM parameter name
 *
 * @author Rizwan Idrees
 */
public record ArrayToolParameter(int index,
                                 Class<?> type,
                                 Class<?> arrayType,
                                 String name,
                                 String description,
                                 boolean required,
                                 String jvmName) implements ToolParameter {

}
