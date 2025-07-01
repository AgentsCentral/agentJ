package ai.agentscentral.core.tool;

/**
 * TypedToolParameter
 *
 * @param index
 * @param type
 * @param name
 * @param description
 * @param required
 * @param jvmName
 *
 * @author Rizwan Idrees
 */
public record TypedToolParameter(int index,
                                 Class<?> type,
                                 String name,
                                 String description,
                                 boolean required,
                                 String jvmName) implements ToolParameter {

}
