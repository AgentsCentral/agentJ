package ai.agentscentral.core.convertors;

/**
 * Functional interface for converting four input values into a single output value.
 *
 * <p>Four-input variant in the convertor family alongside {@link Convertor},
 * {@link BiConvertor}, and {@link TriConvertor}.
 * Implementations are typically provided as lambdas or method references.</p>
 *
 * @param <I1> the type of the first input
 * @param <I2> the type of the second input
 * @param <I3> the type of the third input
 * @param <I4> the type of the fourth input
 * @param <O>  the output type
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface QuadConvertor<I1, I2, I3, I4, O> {

    /**
     * Converts the four given input values into an output value.
     *
     * @param input1 the first input value
     * @param input2 the second input value
     * @param input3 the third input value
     * @param input4 the fourth input value
     * @return the converted output
     */
    O convert(I1 input1, I2 input2, I3 input3, I4 input4);
}
