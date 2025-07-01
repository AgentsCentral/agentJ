package ai.agentscentral.openai.client.request.parameters;

import java.util.List;

/**
 * TextContents
 *
 * @param content
 * @author Rizwan Idrees
 */
public record TextContents(List<ContentPart> content) implements MessageContent {
}
