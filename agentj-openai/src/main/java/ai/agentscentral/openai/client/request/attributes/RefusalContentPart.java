package ai.agentscentral.openai.client.request.attributes;

/**
 * RefusalContentPart
 *
 * @param type
 * @param refusal
 * @author Rizwan Idrees
 */
public record RefusalContentPart(String type, String refusal) implements ContentPart {
}
