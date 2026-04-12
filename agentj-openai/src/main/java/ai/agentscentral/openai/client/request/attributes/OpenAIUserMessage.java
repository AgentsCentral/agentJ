package ai.agentscentral.openai.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * {@link OpenAIMessage} with role {@value #USER}, representing a turn from the human
 * participant.
 *
 * @param role    always {@value #USER}
 * @param name    optional end-user display name for multi-participant scenarios
 * @param content the message body; unwrapped into the enclosing JSON object
 *
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
