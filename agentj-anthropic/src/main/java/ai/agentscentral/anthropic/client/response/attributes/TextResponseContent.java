package ai.agentscentral.anthropic.client.response.attributes;

/**
 * TextResponseContent
 *
 * @param type
 * @param text
 * @author Rizwan Idrees
 */
public record TextResponseContent(ResponseContentType type, String text) implements ResponseContent {
}
