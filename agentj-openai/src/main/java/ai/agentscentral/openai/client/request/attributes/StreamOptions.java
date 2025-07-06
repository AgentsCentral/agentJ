package ai.agentscentral.openai.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * StreamOptions
 *
 * @param includeUsage
 * @author Rizwan Idrees
 */
public record StreamOptions(@JsonProperty("include_usage") boolean includeUsage) {
}
