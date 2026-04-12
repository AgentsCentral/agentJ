package ai.agentscentral.core.session.message;

/**
 * A framework-generated message that pauses tool execution and prompts the user for
 * confirmation or additional input before a tool is invoked.
 *
 * <p>Created by {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} when
 * one or more pending tool calls declare pre-call {@link ai.agentscentral.core.annotation.Interrupt}s.
 * Its {@code parts} are {@link ToolInterruptPart}s, one per interrupt, each carrying the
 * interrupt name, renderer reference, tool call arguments, pre-call results, and the
 * parameters the user must fill in. The message is returned to the caller and persisted
 * in the context; execution resumes when the user replies with a {@link UserMessage}
 * containing the corresponding {@link UserInterruptPart}s.</p>
 *
 * @param contextId the conversation context identifier
 * @param messageId unique identifier for this message
 * @param parts     the interrupt prompts, one {@link ToolInterruptPart} per pending interrupt
 * @param timestamp epoch-millisecond creation timestamp
 *
 * @author Rizwan Idrees
 */
public record ToolInterruptMessage(String contextId,
                                   String messageId,
                                   MessagePart[] parts,
                                   long timestamp) implements Message {

}
