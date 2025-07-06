package ai.agentscentral.openai.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * OpenAIDeveloperMessage
 *
 * @param role
 * @param name
 * @param content
 * @author Rizwan Idrees
 */
public record OpenAIDeveloperMessage(String role, String name,
                                     @JsonUnwrapped MessageContent content) implements OpenAIMessage {

    public static final String DEVELOPER = "developer";

    public String role() {
        return DEVELOPER;
    }
}
