package ai.agentscentral.core.tool;

import java.util.Set;

/**
 * EnumInterruptParameter
 *
 * @param index
 * @param type
 * @param name
 * @param required
 * @param jvmName
 * @param enumValues
 * @author Rizwan Idrees
 */
public record EnumInterruptParameter(int index,
                                     Class<?> type,
                                     String name,
                                     boolean required,
                                     String jvmName,
                                     Set<String> enumValues) implements InterruptParameter {
}
