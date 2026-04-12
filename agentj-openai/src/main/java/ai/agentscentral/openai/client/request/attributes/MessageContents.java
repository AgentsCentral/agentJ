package ai.agentscentral.openai.client.request.attributes;

import java.util.List;

/**
 * {@link MessageContent} implementation that carries an ordered list of typed
 * {@link ContentPart}s, used when the message body is multi-part (e.g. text plus
 * images).
 *
 * @param content ordered list of content parts
 *
 * @author Rizwan Idrees
 */
public record MessageContents(List<? extends ContentPart> content) implements MessageContent {
}
