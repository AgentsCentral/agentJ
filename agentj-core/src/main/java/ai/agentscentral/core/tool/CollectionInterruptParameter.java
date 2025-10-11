package ai.agentscentral.core.tool;

/**
 * CollectionInterruptParameter
 *
 * @param index
 * @param type
 * @param genericType
 * @param name
 * @param required
 * @param jvmName
 * @author Rizwan Idrees
 */
public record CollectionInterruptParameter(int index,
                                           Class<?> type,
                                           Class<?> genericType,
                                           String name,
                                           boolean required,
                                           String jvmName) implements InterruptParameter {

}
