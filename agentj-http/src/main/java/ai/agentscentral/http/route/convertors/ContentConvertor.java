package ai.agentscentral.http.route.convertors;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;

/**
 * Strategy interface for deserialising an inbound request body and serialising an
 * outbound response body, based on the request/response content-type.
 *
 * <p>The primary implementation is {@link DefaultContentConvertor}, which delegates to
 * a list of {@link ContentTypeConvertor}s.  A Jackson-based convertor is registered by
 * default for {@code application/json}.</p>
 *
 * @author Rizwan Idrees
 */
public interface ContentConvertor {

    /**
     * Deserialises the request body into an instance of {@code clazz}, using the
     * request's declared content-type to select the appropriate convertor.
     *
     * @param <T>     the target type
     * @param request the inbound request whose body should be deserialised
     * @param clazz   the target class
     * @return the deserialised object
     */
    <T> T convert(Request request, Class<T> clazz);

    /**
     * Serialises the response body to a string representation, using the response's
     * declared content-type to select the appropriate convertor.
     *
     * @param <T>      the type of the response body
     * @param response the response whose body should be serialised
     * @return the serialised string
     */
    <T> String convert(Response<T> response);

}
