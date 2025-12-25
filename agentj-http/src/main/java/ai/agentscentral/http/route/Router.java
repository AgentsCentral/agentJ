package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;

public interface Router {

    Response route(Request request);

}
