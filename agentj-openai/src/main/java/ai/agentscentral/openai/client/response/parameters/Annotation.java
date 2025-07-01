package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Annotation
 *
 * @param type
 * @param urlCitation
 * @author Rizwan Idrees
 */
public record Annotation(String type, @JsonProperty("url_citation") UrlCitation urlCitation) {
}
