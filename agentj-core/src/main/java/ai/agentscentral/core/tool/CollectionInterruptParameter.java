package ai.agentscentral.core.tool;

/**
 * An {@link InterruptParameter} for {@link java.util.Collection}-typed parameters,
 * capturing the element type via generics reflection.
 *
 * <p>Created by {@link ReflectionUtil#extractCollectionParameter(int, java.lang.reflect.Parameter, ai.agentscentral.core.annotation.InterruptParam)}.</p>
 *
 * @param index       zero-based position in the method's parameter list
 * @param type        the raw collection type (e.g. {@code List.class})
 * @param genericType the element type resolved from the generic type argument; may be
 *                    {@code null} if it cannot be determined
 * @param name        the parameter name from {@link ai.agentscentral.core.annotation.InterruptParam#name()}
 * @param required    whether the user must supply a value
 * @param jvmName     the JVM parameter name
 *
 * @author Rizwan Idrees
 */
public record CollectionInterruptParameter(int index,
                                           Class<?> type,
                                           Class<?> genericType,
                                           String name,
                                           boolean required,
                                           String jvmName) implements InterruptParameter {

}
