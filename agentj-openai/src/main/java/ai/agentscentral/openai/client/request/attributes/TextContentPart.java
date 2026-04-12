package ai.agentscentral.openai.client.request.attributes;

/**
 * {@link ContentPart} carrying plain text content.
 *
 * <p>The {@code type} component always returns {@value #TEXT} via the {@link #type()}
 * override.</p>
 *
 * @param type always {@value #TEXT}
 * @param text the text content
 *
 * @author Rizwan Idrees
 */
public record TextContentPart(String type, String text) implements ContentPart {

    /** Content-part type discriminator for text parts. */
    public static final String TEXT = "text";

    @Override
    public String type() {
        return TEXT;
    }

}
