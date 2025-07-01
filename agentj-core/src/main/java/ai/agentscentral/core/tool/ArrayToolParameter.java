package ai.agentscentral.core.tool;

/**
 * ArrayToolParameter
 * @param index
 * @param type
 * @param arrayType
 * @param name
 * @param description
 * @param required
 * @param jvmName
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
