package ai.agentscentral.http.route.convertors;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;

/**
 * ContentConvertor
 *
 * @author Rizwan Idrees
 */
public interface ContentConvertor {

    <T> T convert(Request request, Class<T> clazz);


    <T> String convert(Response<T> response);

}
