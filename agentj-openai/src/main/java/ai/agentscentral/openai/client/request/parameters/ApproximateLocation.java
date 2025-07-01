package ai.agentscentral.openai.client.request.parameters;

/**
 * ApproximateLocation
 *
 * @param city
 * @param country
 * @param region
 * @param timezone
 * @author Rizwan Idrees
 */
public record ApproximateLocation(String city, String country, String region, String timezone) {
}
