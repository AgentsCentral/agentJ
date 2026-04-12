package ai.agentscentral.core.tool;

/**
 * An {@link InterruptParameter} for primitive and general object parameters.
 *
 * <p>Created by {@link ReflectionUtil#extractPrimitiveParameter(int, java.lang.reflect.Parameter, ai.agentscentral.core.annotation.InterruptParam)}
 * and {@link ReflectionUtil#extractTypedParameter(int, java.lang.reflect.Parameter, ai.agentscentral.core.annotation.InterruptParam)}.</p>
 *
 * @param index    zero-based position in the method's parameter list
 * @param type     the Java type of the parameter
 * @param name     the parameter name from {@link ai.agentscentral.core.annotation.InterruptParam#name()}
 * @param required whether the user must supply a value
 * @param jvmName  the JVM parameter name
 *
 * @author Rizwan Idrees
 */
public record TypedInterruptParameter(int index,
                                      Class<?> type,
                                      String name,
                                      boolean required,
                                      String jvmName) implements InterruptParameter {

}
