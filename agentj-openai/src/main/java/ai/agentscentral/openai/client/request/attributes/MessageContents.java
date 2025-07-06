package ai.agentscentral.openai.client.request.attributes;

import java.util.List;

/**
 * TextContents
 *
 * @param content
 * @author Rizwan Idrees
 */
public record MessageContents(List<ContentPart> content) implements MessageContent {
}
