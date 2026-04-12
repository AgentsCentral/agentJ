package ai.agentscentral.anthropic.client.response.attributes;

import java.util.Map;

/**
 * {@link ResponseContent} implementation for tool-use and handoff invocation blocks
 * ({@link ResponseContentType#tool_use}).
 *
 * <p>When the model decides to call a tool or perform a handoff it emits a block of this
 * type.  {@link ai.agentscentral.anthropic.executor.MessageConvertor} inspects the
 * {@code name} field to classify the block as either a tool-call instruction or a
 * handoff instruction.</p>
 *
 * @param id     the unique identifier for this tool-use block, referenced by the
 *               subsequent {@link ai.agentscentral.anthropic.client.request.attributes.ToolResultContentPart}
 * @param type   the content-block type; always {@link ResponseContentType#tool_use}
 * @param name   the name of the tool or handoff pseudo-tool to invoke
 * @param input  a map of argument name to value supplied by the model; may be empty
 * @param caller optional caller metadata indicating how the tool was invoked
 *               (direct vs. server-side)
 *
 * @author Rizwan Idrees
 */
public record ToolUseResponseContent(String id,
                                     ResponseContentType type,
                                     String name,
                                     Map<String, Object> input,
                                     ToolUseCaller caller) implements ResponseContent {
}
