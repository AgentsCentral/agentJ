package ai.agentscentral.http.response;

import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * The top-level outbound response body returned by an AgentJ agent endpoint.
 *
 * <p>Serialised to JSON by {@link JsonResponseSender}.  The {@code messages} list
 * contains one or more {@link TextMessage} or {@link InterruptMessage} elements
 * representing the agent's response to the current turn.</p>
 *
 * @param sessionId unique identifier of the conversation session
 * @param messages  ordered list of response message parts; must not be {@code null}
 *
 * @author Rizwan Idrees
 */
public record MessageResponse(@Nonnull String sessionId, @Nonnull List<? extends Message> messages) {
}
