package ai.agentscentral.openai.client.request.attributes;

/**
 * {@link ContentPart} carrying a model refusal string.
 *
 * @param type    content-part type discriminator
 * @param refusal the refusal text produced by the model
 *
 * @author Rizwan Idrees
 */
public record RefusalContentPart(String type, String refusal) implements ContentPart {
}
