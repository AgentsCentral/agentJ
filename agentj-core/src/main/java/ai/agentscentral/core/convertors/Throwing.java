package ai.agentscentral.core.convertors;

import java.util.function.Function;

@FunctionalInterface
public interface Throwing<T, R> {

    R apply(T t) throws Exception;

    // "Lifts" a throwing function into a standard Java Function
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
