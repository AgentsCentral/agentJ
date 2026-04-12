package ai.agentscentral.openai.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Options controlling the behaviour of a streamed response, sent only when
 * {@code stream: true} is set in the request.
 *
 * @param includeUsage {@code include_usage} — when {@code true}, the final stream chunk
 *                     includes token usage statistics
 *
 * @author Rizwan Idrees
 */
public record StreamOptions(@JsonProperty("include_usage") boolean includeUsage) {
}
