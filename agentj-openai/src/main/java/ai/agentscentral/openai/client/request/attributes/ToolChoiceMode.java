package ai.agentscentral.openai.client.request.attributes;

/**
 * String-mode values for the {@link ToolChoice} discriminator.
 *
 * @author Rizwan Idrees
 */
public enum ToolChoiceMode implements ToolChoice {

    /** Let the model decide whether to call a tool ({@code "auto"}). */
    auto,

    /** Prevent the model from calling any tools ({@code "none"}). */
    none
}
