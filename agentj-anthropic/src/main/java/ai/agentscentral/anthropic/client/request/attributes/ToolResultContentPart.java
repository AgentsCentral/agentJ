package ai.agentscentral.anthropic.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link ContentPart} representing the result of a previous tool invocation, sent
 * back to the model in a {@code user} role message.
 *
 * <p>The {@link #type()} override always returns {@link #TOOL_RESULT} ({@code "tool_result"}),
 * ignoring the value supplied to the constructor.  {@code toolUseId} must match the
 * {@code id} of the corresponding {@code tool_use} block in the preceding assistant
 * message.</p>
 *
 * @param type      the type discriminator; always overridden to {@link #TOOL_RESULT}
 * @param toolUseId the id of the tool-use block this result corresponds to; mapped to
 *                  {@code tool_use_id}
 * @param content   the string result of the tool execution; may be {@code null} on error
 *
 * @author Rizwan Idrees
 */
public record ToolResultContentPart(String type,
                                    @JsonProperty("tool_use_id") String toolUseId,
                                    String content) implements ContentPart {

    public static String TOOL_RESULT = "tool_result";

    public String type() {
        return TOOL_RESULT;
    }

}
