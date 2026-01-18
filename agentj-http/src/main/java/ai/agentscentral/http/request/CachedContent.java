package ai.agentscentral.http.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

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
