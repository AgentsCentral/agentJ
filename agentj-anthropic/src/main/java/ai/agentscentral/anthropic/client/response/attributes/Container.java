package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Metadata for the server-side execution container returned in a
 * {@link ai.agentscentral.anthropic.client.response.MessagesResponse}.
 *
 * <p>Present when the response involves a code-execution server tool; provides the
 * container's lifecycle information.</p>
 *
 * @param id        the unique identifier of the execution container
 * @param expiresAt the ISO-8601 timestamp at which the container expires; mapped from
 *                  {@code expires_at}
 *
 * @author Rizwan Idrees
 */
public record Container(String id,
                        @JsonProperty("expires_at") String expiresAt) {
}
