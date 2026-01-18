package ai.agentscentral.http.request;

/**
 * RequestExtractor
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface RequestExtractor {

    MessageRequest extract(Request request);
}
