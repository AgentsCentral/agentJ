package ai.agentscentral.anthropic.client.request.attributes;

/**
 * TextPrompt
 *
 * @param type
 * @param text
 * @author Rizwan Idrees
 */
public record TextPrompt(String type, String text) {

    public static final String TYPE = "text";

    public String type() {
        return TYPE;
    }

}
