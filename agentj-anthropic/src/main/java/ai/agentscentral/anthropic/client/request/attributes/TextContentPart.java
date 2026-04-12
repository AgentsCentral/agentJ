package ai.agentscentral.anthropic.client.request.attributes;

/**
 * {@link ContentPart} representing a plain-text block.
 *
 * <p>The {@link #type()} override always returns {@link #TEXT} ({@code "text"}),
 * ignoring the value supplied to the constructor.</p>
 *
 * @param type the type discriminator; always overridden to {@link #TEXT}
 * @param text the text content of this block
 *
 * @author Rizwan Idrees
 */
public record TextContentPart(String type, String text) implements ContentPart {

    public static final String TEXT = "text";

    public String type() {
        return TEXT;
    }

}
