package ai.agentscentral.http.request;

import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * The top-level inbound request body for an AgentJ agent endpoint.
 *
 * <p>Deserialize from JSON by {@link JsonRequestExtractor}.  Each element of
 * {@code messages} is either a {@link TextMessage} or an {@link InterruptMessage},
 * discriminated by the {@code type} field via Jackson polymorphic binding.</p>
 *
 * @param messages the ordered list of message parts sent by the client; must not be
 *                 {@code null}
 *
 * @author Rizwan Idrees
 */
public record MessageRequest(@Nonnull List<? extends Message> messages) {
}
