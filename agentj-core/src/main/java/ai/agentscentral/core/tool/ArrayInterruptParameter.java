package ai.agentscentral.core.tool;

/**
 * An {@link InterruptParameter} for array-typed parameters, capturing the component type.
 *
 * <p>Created by {@link ReflectionUtil#extractArrayParameter(int, java.lang.reflect.Parameter, ai.agentscentral.core.annotation.InterruptParam)}
 * when {@link Class#isArray()} returns {@code true} for the parameter type.</p>
 *
 * @param index     zero-based position in the method's parameter list
 * @param type      the array type (e.g. {@code String[].class})
 * @param arrayType the component type of the array (e.g. {@code String.class}); may be
 *                  {@code null} if it cannot be determined
 * @param name      the parameter name from {@link ai.agentscentral.core.annotation.InterruptParam#name()}
 * @param required  whether the user must supply a value
 * @param jvmName   the JVM parameter name
 *
 * @author Rizwan Idrees
 */
public record ArrayInterruptParameter(int index,
                                      Class<?> type,
                                      Class<?> arrayType,
                                      String name,
                                      boolean required,
                                      String jvmName) implements InterruptParameter {

}
