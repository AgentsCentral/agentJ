package ai.agentscentral.openai.client.request.attributes;

/**
 * {@link MessageContent} implementation that holds a single plain-text string, used
 * when the message content is not multi-part.
 *
 * @param content the raw text content of the message
 *
 * @author Rizwan Idrees
 */
public record TextContent(String content) implements MessageContent {
}
