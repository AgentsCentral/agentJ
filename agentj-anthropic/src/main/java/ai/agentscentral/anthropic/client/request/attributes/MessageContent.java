package ai.agentscentral.anthropic.client.request.attributes;

/**
 * Marker interface for the content payload of an {@link AnthropicMessage}.
 *
 * <p>Concrete implementations:
 * <ul>
 *   <li>{@link TextContent} — a single plain-text string</li>
 *   <li>{@link MessageContentParts} — an ordered list of typed
 *       {@link ContentPart}s (text or tool-result blocks)</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface MessageContent {
}
