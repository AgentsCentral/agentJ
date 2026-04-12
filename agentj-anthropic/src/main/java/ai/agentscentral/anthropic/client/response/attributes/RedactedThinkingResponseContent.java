package ai.agentscentral.anthropic.client.response.attributes;

/**
 * {@link ResponseContent} implementation for redacted extended-thinking blocks
 * ({@link ResponseContentType#redacted_thinking}).
 *
 * <p>Returned when the model's reasoning was filtered by Anthropic's safety systems.
 * The {@code data} field is an opaque, encrypted representation of the redacted
 * content.</p>
 *
 * @param type the content-block type; always
 *             {@link ResponseContentType#redacted_thinking}
 * @param data opaque encrypted data representing the redacted thinking content
 *
 * @author Rizwan Idrees
 */
public record RedactedThinkingResponseContent(ResponseContentType type, String data) implements ResponseContent {
}
