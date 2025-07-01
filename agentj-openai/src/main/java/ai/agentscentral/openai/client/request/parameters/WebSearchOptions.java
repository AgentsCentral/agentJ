package ai.agentscentral.openai.client.request.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * WebSearchOptions
 *
 * @param searchContextSize
 * @param userLocation
 * @author Rizwan Idrees
 */
public record WebSearchOptions(@JsonProperty("search_context_size") SearchContextSize searchContextSize,
                               @JsonProperty("user_location") UserLocation userLocation) {
}
