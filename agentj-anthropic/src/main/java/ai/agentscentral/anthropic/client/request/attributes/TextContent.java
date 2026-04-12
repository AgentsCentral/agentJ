package ai.agentscentral.anthropic.client.request.attributes;

/**
 * {@link MessageContent} implementation representing a single plain-text string.
 *
 * <p>Used when a message has no structured parts — for example as a fallback when
 * the message parts cannot be converted to typed content.</p>
 *
 * @param content the raw text of the message
 *
 * @author Rizwan Idrees
 */
public record TextContent(String content) implements MessageContent {
}
