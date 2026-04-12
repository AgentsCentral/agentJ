package ai.agentscentral.core.convertors;

import java.util.function.Function;

/**
 * Functional interface for a single-argument function that may throw a checked exception.
 *
 * <p>The standard {@link Function} does not permit checked exceptions. {@code Throwing}
 * fills that gap for use cases such as reflective tool invocation, where checked exceptions
 * are unavoidable. The {@link #lift(Throwing)} utility wraps a {@code Throwing} function
 * into a plain {@link Function} by re-throwing any checked exception as an unchecked
 * {@link RuntimeException}.</p>
 *
 * @param <T> the input type
 * @param <R> the return type
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface Throwing<T, R> {

    /**
     * Applies this function to the given argument, potentially throwing a checked exception.
     *
     * @param t the input argument
     * @return the result
     * @throws Exception any checked exception the implementation may raise
     */
    R apply(T t) throws Exception;

    /**
     * Lifts a {@code Throwing} function into a standard {@link Function} by wrapping any
     * checked exception in a {@link RuntimeException}.
     *
     * @param <T> the input type
     * @param <R> the return type
     * @param f   the throwing function to lift
     * @return a {@link Function} that delegates to {@code f} and rethrows checked
     *         exceptions as unchecked
     */
    static <T, R> Function<T, R> lift(Throwing<T, R> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}
