package ai.agentscentral.core.tool;

/**
 * ToolParameter
 *
 * @author Rizwan Idrees
 */
public interface ToolParameter {

    int index();
    Class<?> type();
    String name();
    String description();
    boolean required();

}
