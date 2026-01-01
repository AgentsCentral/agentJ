package ai.agentscentral.http.route.convertors;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public interface ContentConvertor {

    List<ContentTypeConvertor> DEFAULT_CONVERTORS =
            List.of(new JacksonJsonContentTypeConvertor(new ObjectMapper()));

    <T> T convert(Request request, Class<T> clazz);


    <T> String convert(Response<T> response);

}
