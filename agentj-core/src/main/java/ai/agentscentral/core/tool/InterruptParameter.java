package ai.agentscentral.core.tool;

/**
 * ToolParameter
 *
 * @author Rizwan Idrees
 */
public interface InterruptParameter {

    int index();
    Class<?> type();
    String name();
    boolean required();

}
