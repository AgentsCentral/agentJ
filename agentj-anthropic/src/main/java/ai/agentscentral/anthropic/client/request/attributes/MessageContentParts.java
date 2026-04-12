package ai.agentscentral.anthropic.client.request.attributes;

import java.util.List;

/**
 * {@link MessageContent} implementation holding an ordered list of typed
 * {@link ContentPart}s.
 *
 * <p>Used when a message is composed of multiple blocks — for example a user message
 * converted from an AgentJ {@link ai.agentscentral.core.session.message.ToolMessage}
 * will contain {@link ToolResultContentPart}s, while a regular user message will contain
 * {@link TextContentPart}s.</p>
 *
 * @param content the ordered list of content parts; must not be {@code null}
 *
 * @author Rizwan Idrees
 */
public record MessageContentParts(List<? extends ContentPart> content) implements MessageContent {
}
