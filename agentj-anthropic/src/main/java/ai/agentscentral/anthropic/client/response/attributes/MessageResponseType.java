package ai.agentscentral.anthropic.client.response.attributes;

/**
 * The top-level {@code type} field of an Anthropic Messages response.
 *
 * <p>Currently the only defined value is {@link #message}; retained as an enum to
 * support forward-compatible deserialisation.</p>
 *
 * @author Rizwan Idrees
 */
public enum MessageResponseType {
    /** Indicates a standard chat message response. */
    message
}
