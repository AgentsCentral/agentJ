package ai.agentscentral.http.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Package-private cache of a request body, ensuring the underlying
 * {@link java.io.InputStream} is read only once.
 *
 * <p>Created on the first call to {@link ai.agentscentral.http.request.Request#body()},
 * {@link ai.agentscentral.http.request.Request#bytes()}, or
 * {@link ai.agentscentral.http.request.Request#stream()} and held on the
 * {@code Request} instance for all subsequent accesses.</p>
 *
 * @param bytes the fully buffered request body
 */
record CachedContent(byte[] bytes) {

    public String body() {
        return new String(bytes, UTF_8);
    }

    public byte[] bytes() {
        return bytes;
    }

    public InputStream stream() {
        return new ByteArrayInputStream(bytes);
    }

    public static CachedContent from(InputStream inputStream) {
        try {
            return new CachedContent(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
