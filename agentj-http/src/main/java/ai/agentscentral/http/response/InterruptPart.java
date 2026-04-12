package ai.agentscentral.http.response;

import ai.agentscentral.core.session.message.ToolInterruptParameter;

import java.util.List;
import java.util.Map;

/**
 * Describes a single pending tool-call interrupt within an {@link InterruptMessage}.
 *
 * <p>Each part identifies the tool that was called, the parameters the tool already has,
 * and the additional parameters that the user must supply before execution can resume.</p>
 *
 * @param name                 name of the tool that triggered the interrupt
 * @param toolCallId           the LLM-assigned tool-call identifier for correlation
 * @param renderer             optional hint to the client about how to render the input
 *                             form; may be {@code null}
 * @param toolCallParameters   parameters already provided by the model for this tool call
 * @param interruptParameters  parameters that must be supplied by the user to resume
 *
 * @author Rizwan Idrees
 */
public record InterruptPart(String name,
                            String toolCallId,
                            String renderer,
                            Map<String, Object> toolCallParameters,
                            List<ToolInterruptParameter> interruptParameters) {
}
