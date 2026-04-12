package ai.agentscentral.core.session.message;

/**
 * Base interface for all messages stored in a conversation context.
 *
 * <p>Every interaction within a session — user inputs, assistant responses, tool results,
 * handoff records, and interrupt prompts — is represented as a {@code Message}. Messages
 * are persisted by {@link ai.agentscentral.core.context.ContextManager} and retrieved
 * in timestamp order to reconstruct the full conversation history sent to the LLM.</p>
 *
 * <p>Concrete types include {@link UserMessage}, {@link AssistantMessage},
 * {@link ToolMessage}, {@link HandOffMessage}, {@link ToolInterruptMessage},
 * {@link UserInterruptMessage}, and {@link DeveloperMessage}.</p>
 *
 * @author Rizwan Idrees
 */
public interface Message {

    /**
     * Returns the identifier of the conversation context this message belongs to.
     *
     * @return the context (session) id; never {@code null}
     */
    String contextId();

    /**
     * Returns the unique identifier of this message within its context.
     *
     * @return the message id; never {@code null}
     */
    String messageId();

    /**
     * Returns the content parts that make up this message.
     *
     * <p>Each part carries a {@link MessagePartType} and type-specific payload
     * (e.g. text, interrupt prompts, or interrupt responses).</p>
     *
     * @return array of {@link MessagePart}s; never {@code null}, may be empty
     */
    MessagePart[] parts();

    /**
     * Returns the epoch-millisecond timestamp at which this message was created.
     *
     * <p>Used to sort messages into chronological order when rebuilding the context
     * sent to the provider.</p>
     *
     * @return creation timestamp in milliseconds since the Unix epoch
     */
    long timestamp();
}
