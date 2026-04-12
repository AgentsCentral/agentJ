package ai.agentscentral.core.convertors;

/**
 * Functional interface for converting two input values into a single output value.
 *
 * <p>Two-input variant in the convertor family alongside {@link Convertor},
 * {@link TriConvertor}, and {@link QuadConvertor}.
 * Implementations are typically provided as lambdas or method references.</p>
 *
 * @param <I1> the type of the first input
 * @param <I2> the type of the second input
 * @param <O>  the output type
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface BiConvertor<I1, I2, O> {

    /**
     * Converts the two given input values into an output value.
     *
     * @param input1 the first input value
     * @param input2 the second input value
     * @return the converted output
     */
    O convert(I1 input1, I2 input2);

}
