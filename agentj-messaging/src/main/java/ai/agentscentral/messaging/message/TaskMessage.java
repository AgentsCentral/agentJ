package ai.agentscentral.messaging.message;

import java.util.List;

/**
 * TaskMessage
 *
 * @param taskId
 * @param messageParts
 */
public record TaskMessage(String taskId, List<? extends MessagePart> messageParts) {
}
