package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link ToolUseCaller} implementation for server-side code-execution callers.
 *
 * <p>Indicates the tool was invoked by an Anthropic server-side execution environment
 * (e.g. the code-execution sandbox).  Also used as the Jackson default implementation
 * when the {@code type} value does not match any known discriminator.</p>
 *
 * @param type   the caller-type string (e.g. {@code "code_execution_20250825"})
 * @param toolId the server-assigned tool identifier; mapped from {@code tool_id}
 *
 * @author Rizwan Idrees
 */
public record ToolUseServerCaller(String type, @JsonProperty("tool_id") String toolId) implements ToolUseCaller {

}
