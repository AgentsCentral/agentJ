package ai.agentscentral.openai.client.response;

import ai.agentscentral.openai.client.response.parameters.Choice;
import ai.agentscentral.openai.client.response.parameters.Usage;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Wire record for the OpenAI {@code POST /v1/chat/completions} response body.
 *
 * @param id                unique completion identifier (e.g. {@code chatcmpl-...})
 * @param object            object type; always {@code "chat.completion"}
 * @param created           Unix timestamp of when the completion was created
 * @param model             model that generated the completion
 * @param choices           list of generated completion choices
 * @param serviceTier       {@code service_tier} — routing tier used for this request
 * @param systemFingerprint {@code system_fingerprint} — backend configuration
 *                          fingerprint for determinism tracking
 * @param usage             token usage statistics for this request
 *
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
