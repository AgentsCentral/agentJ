package ai.agentscentral.http.route;

record MethodParameter(int index,
                       String name,
                       boolean required,
                       ParameterType type,
                       Class<?> typeClass) {
}
