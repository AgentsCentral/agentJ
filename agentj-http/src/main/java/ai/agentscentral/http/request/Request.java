package ai.agentscentral.http.request;

import ai.agentscentral.http.route.HttpMethod;

import java.util.List;
import java.util.Map;

public record Request(HttpMethod method,
                      String path,
                      Map<String, String[]> parameters,
                      Map<String, List<String>> headers) {

}
