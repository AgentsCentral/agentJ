package ai.agentscentral.openai.client.request.parameters;

/**
 * UserLocation
 *
 * @param type
 * @param approximate
 * @author Rizwan Idrees
 */
public record UserLocation(String type, ApproximateLocation approximate) {
    private static final String APPROXIMATE = "approximate";

    @Override
    public String type() {
        return APPROXIMATE;
    }

}
