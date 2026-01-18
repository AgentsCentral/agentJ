package ai.agentscentral.core.convertors;

import java.util.function.Function;

/**
 * Convertor
 *
 * @param <I>
 * @param <O>
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface Convertor<I, O> {

    O convert(I input);

    static <I, O> Function<I, O> convert(Convertor<I, O> convertor) {
        return convertor::convert;
    }
}
