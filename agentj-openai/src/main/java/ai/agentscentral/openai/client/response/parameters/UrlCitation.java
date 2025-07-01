package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * UrlCitation
 *
 * @param endIndex
 * @param startIndex
 * @param title
 * @param url
 * @author Rizwan Idrees
 */
public record UrlCitation(@JsonProperty("end_index") Integer endIndex,
                          @JsonProperty("start_index") Integer startIndex,
                          String title,
                          String url) {
}
