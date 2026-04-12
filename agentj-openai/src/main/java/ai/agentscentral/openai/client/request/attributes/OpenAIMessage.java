package ai.agentscentral.openai.client.request.attributes;

/**
 * Marker interface for a single message in the OpenAI {@code messages} array.
 *
 * <p>Concrete implementations correspond to the OpenAI role values:
 * <ul>
 *   <li>{@link OpenAIUserMessage} — {@code "user"}</li>
 *   <li>{@link OpenAIAssistantMessage} — {@code "assistant"}</li>
 *   <li>{@link OpenAIDeveloperMessage} — {@code "developer"}</li>
 *   <li>{@link OpenAIToolMessage} — {@code "tool"}</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface OpenAIMessage {

    /**
     * Returns the role discriminator for this message.
     *
     * @return the role string (e.g. {@code "user"}, {@code "assistant"})
     */
    String role();

}
