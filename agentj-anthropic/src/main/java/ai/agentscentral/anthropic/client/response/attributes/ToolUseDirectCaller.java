package ai.agentscentral.anthropic.client.response.attributes;

/**
 * {@link ToolUseCaller} implementation for directly invoked tools ({@code "direct"}).
 *
 * <p>Indicates the tool was called by the model in direct response to the user's
 * request, without an intermediate server execution environment.</p>
 *
 * @param type the caller-type string; always {@code "direct"}
 *
 * @author Rizwan Idrees
 */
public record ToolUseDirectCaller(String type) implements ToolUseCaller {

}
