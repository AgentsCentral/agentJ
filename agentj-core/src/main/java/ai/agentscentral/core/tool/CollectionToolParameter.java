package ai.agentscentral.core.tool;

/**
 * CollectionToolParameter
 *
 * @param index
 * @param type
 * @param genericType
 * @param name
 * @param description
 * @param required
 * @param jvmName
 *
 * @author Rizwan Idrees
 */
public record CollectionToolParameter(int index,
                                      Class<?> type,
                                      Class<?> genericType,
                                      String name,
                                      String description,
                                      boolean required,
                                      String jvmName) implements ToolParameter {

}
