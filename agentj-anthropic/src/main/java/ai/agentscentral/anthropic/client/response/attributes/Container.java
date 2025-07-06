package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Container
 *
 * @param id
 * @param expiresAt
 * @author Rizwan Idrees
 */
public record Container(String id,
                        @JsonProperty("expires_at") String expiresAt) {
}
