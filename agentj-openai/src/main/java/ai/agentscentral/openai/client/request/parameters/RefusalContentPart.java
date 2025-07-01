package ai.agentscentral.openai.client.request.parameters;

/**
 * RefusalContentPart
 *
 * @param type
 * @param refusal
 * @author Rizwan Idrees
 */
public record RefusalContentPart(String type, String refusal) implements ContentPart {
}
