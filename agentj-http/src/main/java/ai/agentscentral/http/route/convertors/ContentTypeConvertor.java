package ai.agentscentral.http.route.convertors;

/**
 * Low-level strategy interface for a single content-type serialisation/deserialisation
 * pair.
 *
 * <p>Implementations declare which media-type(s) they handle via {@link #matches(String)}
 * and are registered with a {@link DefaultContentConvertor}.  The Jackson-based
 * implementation is {@link JacksonJsonContentTypeConvertor}.</p>
 *
 * @author Rizwan Idrees
 */
public interface ContentTypeConvertor {

    /**
     * Serialises the given object to a string representation.
     *
     * @param o the object to serialise; may be {@code null} depending on implementation
     * @return the serialised string
     */
    String serialize(Object o);

    /**
     * Deserialises the given string body into an instance of {@code clazz}.
     *
     * @param <T>   the target type
     * @param body  the raw string body
     * @param clazz the target class
     * @return the deserialised object
     */
    <T> T deserialize(String body, Class<T> clazz);

    /**
     * Returns {@code true} if this convertor handles the given media type.
     *
     * @param mediaType the {@code Content-Type} value to test
     * @return {@code true} if this convertor can handle the media type
     */
    boolean matches(String mediaType);

}
