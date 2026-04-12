package ai.agentscentral.openai.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Configuration for the web-search grounding feature, passed as
 * {@code web_search_options} in a
 * {@link ai.agentscentral.openai.client.request.CompletionRequest}.
 *
 * @param searchContextSize {@code search_context_size} — controls how much web content
 *                          is retrieved per search query
 * @param userLocation      {@code user_location} — optional geographic hint used to
 *                          localise search results
 *
 * @author Rizwan Idrees
 */
public record WebSearchOptions(@JsonProperty("search_context_size") SearchContextSize searchContextSize,
                               @JsonProperty("user_location") UserLocation userLocation) {
}
