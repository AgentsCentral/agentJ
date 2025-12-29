package ai.agentscentral.http.route;

/**
 * MethodParameter
 *
 * @param index
 * @param name
 * @param type
 * @param typeClass
 * @author Rizwan Idrees
 */
record MethodParameter(int index,
                       String name,
                       ParameterType type,
                       Class<?> typeClass) {
}
