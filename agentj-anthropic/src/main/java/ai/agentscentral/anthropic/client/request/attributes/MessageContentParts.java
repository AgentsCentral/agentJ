package ai.agentscentral.anthropic.client.request.attributes;

import java.util.List;

/**
 * TextContents
 *
 * @param content
 * @author Rizwan Idrees
 */
public record MessageContentParts(List<? extends ContentPart> content) implements MessageContent {
}
