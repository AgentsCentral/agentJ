package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A single completion choice returned in a {@link ai.agentscentral.openai.client.response.CompletionResponse}.
 *
 * @param index        zero-based index of this choice in the response array
 * @param finishReason reason the model stopped generating tokens (e.g. {@code "stop"},
 *                     {@code "tool_calls"}, {@code "length"})
 * @param logProbs     per-token log-probability information; {@code null} unless
 *                     {@code logprobs} was requested
 * @param message      the generated assistant message for this choice
 *
 * @author Rizwan Idrees
 */
public record Choice(@JsonProperty("index") Integer index,
                     @JsonProperty("finish_reason") String finishReason,
                     @JsonProperty("logprobs") LogProbs logProbs,
                     ChoiceMessage message
) {
}
