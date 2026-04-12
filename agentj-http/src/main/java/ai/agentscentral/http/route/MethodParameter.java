package ai.agentscentral.http.route;

/**
 * Metadata descriptor for a single parameter of a controller method, used by
 * {@link HttpControllerRouter} to bind request data to method arguments.
 *
 * @param index     zero-based position of the parameter in the method signature;
 *                  used to order arguments before reflective invocation
 * @param name      the binding name taken from the annotation (e.g. the value of
 *                  {@code @RequestParam("id")} or {@code @PathVariable("id")})
 * @param type      the {@link ParameterType} indicating where the value comes from
 *                  (query string, path, header, or body)
 * @param typeClass the Java type to convert the raw string value to
 *
 * @author Rizwan Idrees
 */
record MethodParameter(int index,
                       String name,
                       ParameterType type,
                       Class<?> typeClass) {
}
