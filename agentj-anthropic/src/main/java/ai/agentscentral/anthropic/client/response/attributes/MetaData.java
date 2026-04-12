package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Optional metadata attached to an Anthropic Messages request.
 *
 * <p>Currently carries only the end-user identifier, which Anthropic uses for
 * abuse-detection purposes.  Sent as the {@code metadata} field in the request.</p>
 *
 * @param userId an opaque string identifying the end user; mapped to {@code user_id}
 *
 * @author Rizwan Idrees
 */
public record MetaData(@JsonProperty("user_id") String userId) {
}
