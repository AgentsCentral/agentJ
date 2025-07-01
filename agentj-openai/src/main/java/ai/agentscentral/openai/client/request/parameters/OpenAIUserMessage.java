package ai.agentscentral.openai.client.request.parameters;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * OpenAIUserMessage
 *
 * @param role
 * @param name
 * @param content
 * @author Rizwan Idrees
 */
public record OpenAIUserMessage(String role,
                                String name,
                                @JsonUnwrapped MessageContent content) implements OpenAIMessage {

    public static final String USER = "user";

    public String role() {
        return USER;
    }

}
