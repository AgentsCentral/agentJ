package ai.agentscentral.core.session.message;

/**
 * A single content unit within a {@link Message}.
 *
 * <p>Messages are composed of one or more parts, each carrying a {@link MessagePartType}
 * discriminator and type-specific data. Concrete implementations include
 * {@link TextPart} (plain text or thinking content), {@link ToolInterruptPart}
 * (interrupt prompt sent to the user before a tool call), and {@link UserInterruptPart}
 * (the user's interrupt response).</p>
 *
 * @author Rizwan Idrees
 */
public interface MessagePart {

    /**
     * Returns the type of content carried by this part.
     *
     * @return the {@link MessagePartType}; never {@code null}
     */
    MessagePartType type();
}
