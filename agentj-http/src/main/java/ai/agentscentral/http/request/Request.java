package ai.agentscentral.http.request;

import ai.agentscentral.http.route.HttpMethod;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Framework-internal representation of an HTTP request.
 *
 * <p>Created by {@link ai.agentscentral.http.request.convertors.RequestConvertors#servletRequestToRequest} from a
 * Jakarta {@link jakarta.servlet.http.HttpServletRequest} and passed to every
 * {@link ai.agentscentral.http.route.Route} and
 * {@link ai.agentscentral.http.handler.HttpHandler} in the processing chain.</p>
 *
 * <p>The request body is read lazily and cached on the first call to {@link #body()},
 * {@link #bytes()}, or {@link #stream()} to allow multiple reads without consuming the
 * underlying {@link java.io.InputStream} more than once.</p>
 *
 * @author Rizwan Idrees
 */
public class Request {

    private final HttpMethod method;
    private final String path;
    private final InputStream stream;
    private final Long contentLength;
    private final String uri;
    private final Map<String, String[]> parameters;
    private final Map<String, List<String>> headers;

    private CachedContent cachedContent;

    /**
     * Creates a new {@code Request}.
     *
     * @param method        the HTTP method of the request
     * @param path          the servlet path (without query string)
     * @param stream        the raw body input stream; read lazily and cached
     * @param contentLength the value of the {@code Content-Length} header, or
     *                      {@code -1} if unknown
     * @param uri           the full request URI including path and query string
     * @param parameters    the parsed query/form parameters
     * @param headers       all request headers, each name mapped to its value list
     */
    public Request(HttpMethod method,
                   String path,
                   InputStream stream,
                   Long contentLength,
                   String uri,
                   Map<String, String[]> parameters,
                   Map<String, List<String>> headers) {

        this.method = method;
        this.path = path;
        this.stream = stream;
        this.contentLength = contentLength;
        this.uri = uri;
        this.parameters = parameters;
        this.headers = headers;
    }

    public HttpMethod method() {
        return method;
    }

    public String getHeader(String name) {
        final List<String> values = headers.get(name);
        return Optional.ofNullable(values)
                .filter(v -> !v.isEmpty())
                .map(List::getFirst)
                .orElse(null);
    }

    public String body() {
        cachedContent = Optional.ofNullable(cachedContent)
                .orElseGet(() -> CachedContent.from(stream));
        return cachedContent.body();
    }

    public byte[] bytes() {
        cachedContent = Optional.ofNullable(cachedContent)
                .orElseGet(() -> CachedContent.from(stream));

        return cachedContent.bytes();
    }

    public InputStream stream() {
        return Optional.ofNullable(cachedContent)
                .map(CachedContent::stream).orElse(stream);
    }

    public String uri() {
        return uri;
    }

    public String path() {
        return path;
    }

    public Map<String, String[]> parameters() {
        return parameters;
    }

    public Map<String, List<String>> headers() {
        return headers;
    }

    public Long contentLength() {
        return contentLength;
    }
}
