package ai.agentscentral.http.request;

import jakarta.annotation.Nonnull;

/**
 * MessageRequest
 *
 * @param message
 * @author Rizwan Idrees
 */
public record MessageRequest(@Nonnull String message) {
}
