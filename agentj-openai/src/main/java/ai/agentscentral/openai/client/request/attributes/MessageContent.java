package ai.agentscentral.openai.client.request.attributes;

/**
 * Marker interface for the content field of an {@link OpenAIMessage}.
 *
 * <p>Concrete implementations:</p>
 * <ul>
 *   <li>{@link TextContent} — a plain string value</li>
 *   <li>{@link MessageContents} — an array of typed content parts</li>
 * </ul>
 * <p>Both are serialized with {@code @JsonUnwrapped} so the content field is flattened
 * directly into the parent message object.</p>
 *
 * @author Rizwan Idrees
 */
public interface MessageContent {
}
