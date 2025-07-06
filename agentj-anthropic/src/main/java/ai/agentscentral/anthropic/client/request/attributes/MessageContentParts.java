package ai.agentscentral.anthropic.client.request.attributes;

import java.util.List;

/**
 * TextContents
 *
 * @param content
 * @author Rizwan Idrees
 */
public record MessageContentParts(List<ContentPart> content) implements MessageContent {
}
