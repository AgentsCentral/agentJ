package ai.agentscentral.http.request;

/**
 * A single named parameter value supplied by the user in an
 * {@link InterruptMessage}.
 *
 * <p>Each entry corresponds to one
 * {@link ai.agentscentral.core.session.message.ToolInterruptParameter} that was
 * declared on the interrupted tool and presented to the user for input.</p>
 *
 * @param name  the parameter name, matching the interrupt parameter's
 *              {@link ai.agentscentral.core.session.message.ToolInterruptParameter#name()}
 * @param value the value provided by the user; coerced to the expected type during
 *              tool invocation
 *
 * @author Rizwan Idrees
 */
public record InterruptParameter(String name, Object value) {
}
