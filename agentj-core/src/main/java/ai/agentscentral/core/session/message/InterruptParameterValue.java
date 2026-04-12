package ai.agentscentral.core.session.message;

/**
 * A name-value pair representing a single user-supplied value for an interrupt parameter.
 *
 * <p>Carried inside a {@link UserInterruptPart} and consumed by
 * {@link ai.agentscentral.core.tool.DefaultToolCallExecutor}, which injects the value
 * into the tool method for the corresponding
 * {@link ai.agentscentral.core.annotation.InterruptParam}-annotated parameter.</p>
 *
 * @param name  the parameter name, matching {@link ToolInterruptParameter#name()}
 * @param value the value provided by the user; type must be compatible with the
 *              annotated method parameter
 *
 * @author Rizwan Idrees
 */
public record InterruptParameterValue(String name, Object value) {
}
