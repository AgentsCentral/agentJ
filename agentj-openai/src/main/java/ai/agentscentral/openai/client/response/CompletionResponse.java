package ai.agentscentral.openai.client.response;

import ai.agentscentral.openai.client.response.parameters.Choice;
import ai.agentscentral.openai.client.response.parameters.Usage;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * CompletionResponse
 *
 * @param id
 * @param object
 * @param created
 * @param model
 * @param choices
 * @param serviceTier
 * @param systemFingerprint
 * @param usage
 * @author Rizwan Idrees
 */
public record CompletionResponse(String id,
                                 String object,
                                 Long created,
                                 String model,
                                 List<Choice> choices,
                                 @JsonProperty("service_tier") String serviceTier,
                                 @JsonProperty("system_fingerprint") String systemFingerprint,
                                 Usage usage) {
}
