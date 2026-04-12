package ai.agentscentral.http.request;

/**
 * Functional interface for deserialising an HTTP request body into a
 * {@link MessageRequest}.
 *
 * <p>The default implementation is
 * {@link JsonRequestExtractor}, which reads the request body as JSON and binds it to
 * the {@link MessageRequest} record.  Custom implementations can support other
 * content types or apply additional validation.</p>
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface RequestExtractor {

    /**
     * Extracts a {@link MessageRequest} from the given HTTP request.
     *
     * @param request the incoming HTTP request
     * @return the deserialize message request; never {@code null}
     */
    MessageRequest extract(Request request);
}
