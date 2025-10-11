package ai.agentscentral.core.tool;

/**
 * ArrayInterruptParameter
 *
 * @param index
 * @param type
 * @param arrayType
 * @param name
 * @param required
 * @param jvmName
 * @author Rizwan Idrees
 */
public record ArrayInterruptParameter(int index,
                                      Class<?> type,
                                      Class<?> arrayType,
                                      String name,
                                      boolean required,
                                      String jvmName) implements InterruptParameter {

}
