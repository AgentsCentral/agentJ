package ai.agentscentral.core.tool;

import java.util.Set;

/**
 * EnumToolParameter
 *
 * @param index
 * @param type
 * @param name
 * @param description
 * @param required
 * @param jvmName
 * @param enumValues
 * @author Rizwan Idrees
 */
public record EnumToolParameter(int index,
                                Class<?> type,
                                String name,
                                String description,
                                boolean required,
                                String jvmName,
                                Set<String> enumValues) implements ToolParameter {
}
