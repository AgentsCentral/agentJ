package ai.agentscentral.core.tool;

import java.util.Map;

/**
 * Represents a tool invocation request emitted by the LLM provider within an
 * {@link ai.agentscentral.core.session.message.AssistantMessage}.
 *
 * <p>When the model decides to call a tool, the provider layer parses the call details
 * into a {@code ToolCallInstruction} and attaches it to the assistant message.
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} then uses the
 * instruction to look up the corresponding {@link ToolCall} descriptor and delegates
 * execution to {@link DefaultToolCallExecutor}.</p>
 *
 * <p>The default implementation is {@link DefaultToolCallInstruction}.</p>
 *
 * @author Rizwan Idrees
 */
public interface ToolCallInstruction {

    /**
     * Returns the unique call identifier assigned by the provider to this tool invocation.
     *
     * <p>Used to correlate the invocation with its {@link ai.agentscentral.core.session.message.ToolMessage}
     * result when building the next provider request.</p>
     *
     * @return the call id; never {@code null}
     */
    String id();

    /**
     * Returns the name of the tool to invoke, matching a key in the agent's tool map.
     *
     * @return the tool name; never {@code null}
     */
    String name();

    /**
     * Returns the raw JSON string of arguments as received from the provider.
     *
     * @return the unparsed argument payload; never {@code null}
     */
    String rawArguments();

    /**
     * Returns the parsed arguments as a name-to-value map.
     *
     * <p>Values are typed according to the JSON parsing rules of the provider client and
     * are matched to {@link ToolParameter}s by name during method invocation.</p>
     *
     * @return a map of argument names to their values; never {@code null}, may be empty
     */
    Map<String, Object> arguments();

}
