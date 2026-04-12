package ai.agentscentral.core.session.message;

/**
 * Discriminator enum identifying the kind of content carried by a {@link MessagePart}.
 *
 * @author Rizwan Idrees
 */
public enum MessagePartType {

    /** Plain text content in an assistant or developer message, or a model thinking block. */
    text,

    /** Model reasoning / thinking content produced by providers that expose chain-of-thought. */
    thinking,

    /**
     * An interrupt prompt emitted by the framework before executing a tool call,
     * represented by a {@link ToolInterruptPart}.
     */
    tool_interrupt,

    /**
     * The user's response to a tool interrupt, represented by a {@link UserInterruptPart}
     * and carried inside a {@link UserMessage}.
     */
    user_interrupt
}
