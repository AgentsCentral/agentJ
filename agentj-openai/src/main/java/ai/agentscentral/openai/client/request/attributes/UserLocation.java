package ai.agentscentral.openai.client.request.attributes;

/**
 * Geographic location hint within {@link WebSearchOptions}, used to localise web search
 * results.
 *
 * <p>The {@code type} component always returns {@code "approximate"} via the
 * {@link #type()} override.</p>
 *
 * @param type        always {@code "approximate"}
 * @param approximate coarse-grained location details (city, country, region, timezone)
 *
 * @author Rizwan Idrees
 */
public record UserLocation(String type, ApproximateLocation approximate) {
    private static final String APPROXIMATE = "approximate";

    @Override
    public String type() {
        return APPROXIMATE;
    }

}
