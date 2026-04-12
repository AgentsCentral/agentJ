package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * Polymorphic interface describing how a tool-use block was triggered in an Anthropic
 * response.
 *
 * <p>Jackson deserialises this field using the {@code type} property:
 * <ul>
 *   <li>{@link ToolUseDirectCaller} ({@code "direct"}) — the tool was called directly
 *       by the user's request</li>
 *   <li>{@link ToolUseServerCaller} ({@code "code_execution_*"}) — the tool was called
 *       by a server-side execution environment</li>
 * </ul>
 * If no type matches, {@link ToolUseServerCaller} is used as the default implementation.
 *
 * @author Rizwan Idrees
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true, defaultImpl = ToolUseServerCaller.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ToolUseDirectCaller.class, name = "direct"),
        @JsonSubTypes.Type(value = ToolUseServerCaller.class, names = {"code_execution_20250825", "code_execution_20260120"})
})
public interface ToolUseCaller {

    /**
     * Returns the caller-type discriminator used by Jackson for polymorphic
     * deserialisation.
     *
     * @return the caller type string
     */
    String type();
}
