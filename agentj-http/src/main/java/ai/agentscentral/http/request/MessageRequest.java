package ai.agentscentral.http.request;

import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * MessageRequest
 *
 * @param messages
 * @author Rizwan Idrees
 */
public record MessageRequest(@Nonnull List<? extends Message> messages) {
}
