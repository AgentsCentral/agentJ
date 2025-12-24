package ai.agentscentral.http.request;

import ai.agentscentral.http.route.HttpMethod;

public record Request(HttpMethod method, String path) {

}
