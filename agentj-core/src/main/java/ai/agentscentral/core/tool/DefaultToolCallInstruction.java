package ai.agentscentral.core.tool;

import java.util.Map;

/**
 * Default immutable implementation of {@link ToolCallInstruction}.
 *
 * <p>Created by the provider layer when it parses a tool call from an assistant response
 * and attached to the corresponding
 * {@link ai.agentscentral.core.session.message.AssistantMessage}.</p>
 *
 * @param id           the unique call id assigned by the provider
 * @param name         the name of the tool to invoke
 * @param rawArguments the raw JSON argument string as received from the provider
 * @param arguments    the parsed arguments as a name-to-value map
 *
 * @author Rizwan Idrees
 */
public record DefaultToolCallInstruction(String id,
                                         String name,
                                         String rawArguments,
                                         Map<String, Object> arguments)
        implements ToolCallInstruction {
}
