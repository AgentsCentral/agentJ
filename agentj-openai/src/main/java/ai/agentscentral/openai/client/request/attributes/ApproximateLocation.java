package ai.agentscentral.openai.client.request.attributes;

/**
 * Coarse-grained geographic location used as a web-search hint within
 * {@link UserLocation}.
 *
 * @param city     city name (e.g. {@code "London"}); may be {@code null}
 * @param country  two-letter ISO country code (e.g. {@code "GB"}); may be {@code null}
 * @param region   region or state name; may be {@code null}
 * @param timezone IANA timezone identifier (e.g. {@code "Europe/London"}); may be
 *                 {@code null}
 *
 * @author Rizwan Idrees
 */
public record ApproximateLocation(String city, String country, String region, String timezone) {
}
