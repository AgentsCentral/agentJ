package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * ToolUseCaller
 *
 * @author Rizwan Idrees
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true, defaultImpl = ToolUseServerCaller.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ToolUseDirectCaller.class, name = "direct"),
        @JsonSubTypes.Type(value = ToolUseServerCaller.class, names = {"code_execution_20250825", "code_execution_20260120"})
})
public interface ToolUseCaller {

    String type();
}
