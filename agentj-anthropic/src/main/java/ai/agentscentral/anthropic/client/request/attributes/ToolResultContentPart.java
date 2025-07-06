package ai.agentscentral.anthropic.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ToolResultContentPart(String type,
                                    @JsonProperty("tool_use_id") String toolUseId,
                                    String content) implements ContentPart {

    public static String TOOL_RESULT = "tool_result";

    public String type() {
        return TOOL_RESULT;
    }

}
