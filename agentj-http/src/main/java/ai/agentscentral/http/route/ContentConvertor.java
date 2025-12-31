package ai.agentscentral.http.route;

public interface ContentConvertor {


    String serialize(Object o);

    <T> T deserialize(String body, Class<T> clazz);

}
