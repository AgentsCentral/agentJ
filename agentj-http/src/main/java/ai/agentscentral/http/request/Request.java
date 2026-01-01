package ai.agentscentral.http.request;

import ai.agentscentral.http.route.HttpMethod;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record Request(HttpMethod method,
                      String path,
                      String body,
                      Map<String, String[]> parameters,
                      Map<String, List<String>> headers) {


    public String getHeader(String name){
        final List<String> values = headers.get(name);
        return Optional.ofNullable(values)
                .filter(v-> !v.isEmpty())
                .map(List::getFirst)
                .orElse(null);
    }

}
