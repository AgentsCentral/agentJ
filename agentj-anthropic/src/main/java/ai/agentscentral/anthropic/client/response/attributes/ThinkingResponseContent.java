package ai.agentscentral.anthropic.client.response.attributes;

/**
 * ThinkingResponseContent
 *
 * @param type
 * @param signature
 * @param thinking
 * @author Rizwan Idrees
 */
public record ThinkingResponseContent(ResponseContentType type,
                                      String signature,
                                      String thinking) implements ResponseContent {
}
