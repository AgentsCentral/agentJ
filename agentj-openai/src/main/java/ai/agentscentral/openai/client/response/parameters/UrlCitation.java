package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * URL citation details within an {@link Annotation}, pointing to a web source used by
 * the model's web-search grounding.
 *
 * @param endIndex   {@code end_index} — character offset of the end of the cited span in
 *                   the message content
 * @param startIndex {@code start_index} — character offset of the start of the cited
 *                   span
 * @param title      human-readable title of the cited web page
 * @param url        URL of the cited source
 *
 * @author Rizwan Idrees
 */
public record UrlCitation(@JsonProperty("end_index") Integer endIndex,
                          @JsonProperty("start_index") Integer startIndex,
                          String title,
                          String url) {
}
