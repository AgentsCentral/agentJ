package ai.agentscentral.http.route.convertors;

public interface ContentTypeConvertor {

    String serialize(Object o);

    <T> T deserialize(String body, Class<T> clazz);

    boolean matches(String mediaType);

}
