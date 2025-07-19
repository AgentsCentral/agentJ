package ai.agentscentral.http.response;

import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * MessageResponse
 *
 * @param sessionId
 * @param messages
 * @author Rizwan Idrees
 */
public record MessageResponse(@Nonnull String sessionId, @Nonnull List<String> messages) {
}
