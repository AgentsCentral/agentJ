package ai.agentscentral.http.handler;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;

@FunctionalInterface
public interface HttpHandler extends Handler {

    <O> Response<O> handle(Request request);

}
