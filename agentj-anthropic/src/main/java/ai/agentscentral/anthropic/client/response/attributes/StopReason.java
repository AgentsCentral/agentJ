package ai.agentscentral.anthropic.client.response.attributes;

/**
 * The reason the Anthropic model stopped generating tokens, as reported in the
 * {@link ai.agentscentral.anthropic.client.response.MessagesResponse#stopReason()} field.
 *
 * @author Rizwan Idrees
 */
public enum StopReason {
    /** The model reached a natural stopping point. */
    end_turn,
    /** The response reached the configured {@code max_tokens} limit. */
    max_tokens,
    /** A configured stop sequence was encountered. */
    stop_sequence,
    /** The model is requesting one or more tool calls. */
    tool_use,
    /** The model paused mid-turn (e.g. for a human-in-the-loop interaction). */
    pause_turn,
    /** Generation was stopped due to a content policy refusal. */
    refusal
}
