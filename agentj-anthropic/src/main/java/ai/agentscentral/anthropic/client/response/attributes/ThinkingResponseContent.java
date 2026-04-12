package ai.agentscentral.anthropic.client.response.attributes;

/**
 * {@link ResponseContent} implementation for extended-thinking blocks
 * ({@link ResponseContentType#thinking}).
 *
 * <p>The model's step-by-step reasoning is exposed in the {@code thinking} field.
 * The {@code signature} is an opaque value provided by Anthropic for verification
 * purposes.</p>
 *
 * @param type      the content-block type; always {@link ResponseContentType#thinking}
 * @param signature an opaque signature string for the thinking block
 * @param thinking  the model's raw reasoning text
 *
 * @author Rizwan Idrees
 */
public record ThinkingResponseContent(ResponseContentType type,
                                      String signature,
                                      String thinking) implements ResponseContent {
}
