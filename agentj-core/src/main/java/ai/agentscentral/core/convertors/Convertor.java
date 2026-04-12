package ai.agentscentral.core.convertors;

import java.util.function.Function;

/**
 * Functional interface for converting a single input value into an output value.
 *
 * <p>Serves as a single-input variant in the convertor family alongside
 * {@link BiConvertor}, {@link TriConvertor}, and {@link QuadConvertor}.
 * Implementations are typically provided as lambdas or method references.</p>
 *
 * @param <I> the input type
 * @param <O> the output type
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface Convertor<I, O> {

    /**
     * Converts the given input into an output value.
     *
     * @param input the value to convert
     * @return the converted output
     */
    O convert(I input);

    /**
     * Adapts a {@code Convertor} into a standard {@link Function}.
     *
     * <p>Useful when an API requires a {@link Function} but the conversion logic is
     * expressed as a {@code Convertor}.</p>
     *
     * @param <I>       the input type
     * @param <O>       the output type
     * @param convertor the convertor to adapt
     * @return a {@link Function} delegating to {@code convertor::convert}
     */
    static <I, O> Function<I, O> convert(Convertor<I, O> convertor) {
        return convertor::convert;
    }
}
