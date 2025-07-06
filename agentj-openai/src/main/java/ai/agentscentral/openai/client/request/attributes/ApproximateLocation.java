package ai.agentscentral.openai.client.request.attributes;

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
