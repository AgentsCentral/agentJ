package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MetaData(@JsonProperty("user_id") String userId) {
}
