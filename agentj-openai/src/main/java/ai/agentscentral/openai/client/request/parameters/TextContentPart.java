package ai.agentscentral.openai.client.request.parameters;

/**
 * TextContentPart
 *
 * @param type
 * @param text
 * @author Rizwan Idrees
 */
public record TextContentPart(String type, String text) implements ContentPart {
}
