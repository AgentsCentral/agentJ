package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A structured annotation on a {@link ChoiceMessage}, such as a web-search citation.
 *
 * @param type        annotation type discriminator (e.g. {@code "url_citation"})
 * @param urlCitation {@code url_citation} — URL citation details; present when
 *                    {@code type} is {@code "url_citation"}
 *
 * @author Rizwan Idrees
 */
public record Annotation(String type, @JsonProperty("url_citation") UrlCitation urlCitation) {
}
