package ai.agentscentral.openai.client.request.attributes;

/**
 * TextResponseFormat
 *
 * @param type
 * @author Rizwan Idrees
 */
public record TextResponseFormat(String type) implements ResponseFormat {

    private static final String TEXT = "text";

    @Override
    public String type() {
        return TEXT;
    }

}
