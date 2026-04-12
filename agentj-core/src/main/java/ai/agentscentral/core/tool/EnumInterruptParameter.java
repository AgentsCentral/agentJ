package ai.agentscentral.core.tool;

import java.util.Set;

/**
 * An {@link InterruptParameter} for enum-typed parameters, additionally exposing the set
 * of valid constant values so the interrupt UI can restrict input to valid choices.
 *
 * <p>Created by {@link ReflectionUtil#extractEnumParameter(int, java.lang.reflect.Parameter, ai.agentscentral.core.annotation.InterruptParam)}
 * when {@link Class#isEnum()} returns {@code true} for the parameter type.</p>
 *
 * @param index      zero-based position in the method's parameter list
 * @param type       the enum type
 * @param name       the parameter name from {@link ai.agentscentral.core.annotation.InterruptParam#name()}
 * @param required   whether the user must supply a value
 * @param jvmName    the JVM parameter name
 * @param enumValues the string representations of all declared enum constants
 *
 * @author Rizwan Idrees
 */
public record EnumInterruptParameter(int index,
                                     Class<?> type,
                                     String name,
                                     boolean required,
                                     String jvmName,
                                     Set<String> enumValues) implements InterruptParameter {
}
