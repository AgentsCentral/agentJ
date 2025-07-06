package ai.agentscentral.anthropic.client.request.attributes;

/**
 * TextContentPart
 *
 * @param type
 * @param text
 * @author Rizwan Idrees
 */
public record TextContentPart(String type, String text) implements ContentPart {

    public static final String TEXT = "text";

    public String type() {
        return TEXT;
    }

}
