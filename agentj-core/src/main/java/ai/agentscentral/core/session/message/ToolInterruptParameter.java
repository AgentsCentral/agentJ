package ai.agentscentral.core.session.message;

/**
 * Describes a single parameter that the user must fill in when responding to a
 * {@link ToolInterruptPart}.
 *
 * <p>Derived from the {@link ai.agentscentral.core.tool.InterruptParameter} metadata
 * extracted by {@link ai.agentscentral.core.tool.ToolBagToolsExtractor} and included in
 * the interrupt prompt so the UI layer knows which inputs to render.</p>
 *
 * @param name     the parameter name, matching the corresponding
 *                 {@link ai.agentscentral.core.annotation.InterruptParam#name()} on the
 *                 tool method
 * @param required whether the user must provide a value for this parameter
 *
 * @author Rizwan Idrees
 */
public record ToolInterruptParameter(String name, boolean required) {
}
