package ai.agentscentral.core.tool;

/**
 * TypedInterruptParameter
 *
 * @param index
 * @param type
 * @param name
 * @param required
 * @param jvmName
 *
 * @author Rizwan Idrees
 */
public record TypedInterruptParameter(int index,
                                      Class<?> type,
                                      String name,
                                      boolean required,
                                      String jvmName) implements InterruptParameter {

}
