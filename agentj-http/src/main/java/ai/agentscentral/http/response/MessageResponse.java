package ai.agentscentral.http.response;

import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * MessageResponse
 *
 * @param conversationId
 * @param messages
 * @author Rizwan Idrees
 */
public record MessageResponse(@Nonnull String conversationId, @Nonnull List<String> messages) {
}
