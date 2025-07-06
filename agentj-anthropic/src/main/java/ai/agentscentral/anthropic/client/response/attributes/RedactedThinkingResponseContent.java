package ai.agentscentral.anthropic.client.response.attributes;

/**
 * RedactedThinkingResponseContent
 *
 * @param type
 * @param data
 * @author Rizwan Idrees
 */
public record RedactedThinkingResponseContent(ResponseContentType type, String data) implements ResponseContent {
}
