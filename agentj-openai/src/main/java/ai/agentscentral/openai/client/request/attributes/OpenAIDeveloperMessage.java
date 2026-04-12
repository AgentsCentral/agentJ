package ai.agentscentral.openai.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * {@link OpenAIMessage} with role {@value #DEVELOPER}, used to inject system-level
 * instructions into the conversation (equivalent to the older {@code "system"} role).
 *
 * @param role    always {@value #DEVELOPER}
 * @param name    optional display name for the instruction source
 * @param content the developer instructions; unwrapped into the enclosing JSON object
 *
 * @author Rizwan Idrees
 */
public record OpenAIDeveloperMessage(String role, String name,
                                     @JsonUnwrapped MessageContent content) implements OpenAIMessage {

    public static final String DEVELOPER = "developer";

    public String role() {
        return DEVELOPER;
    }
}
