package ai.agentscentral.core.convertors;

/**
 * Functional interface for converting three input values into a single output value.
 *
 * <p>Three-input variant in the convertor family alongside {@link Convertor},
 * {@link BiConvertor}, and {@link QuadConvertor}.
 * Used in the framework, for example, as the conversion callback passed to
 * {@link ai.agentscentral.core.tool.ResultOrError#onResult} to transform a tool-call
 * result into a {@link ai.agentscentral.core.session.message.ToolMessage}.
 * Implementations are typically provided as lambdas or method references.</p>
 *
 * @param <I1> the type of the first input
 * @param <I2> the type of the second input
 * @param <I3> the type of the third input
 * @param <O>  the output type
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface TriConvertor<I1, I2, I3, O> {

    /**
     * Converts the three given input values into an output value.
     *
     * @param input1 the first input value
     * @param input2 the second input value
     * @param input3 the third input value
     * @return the converted output
     */
    O convert(I1 input1, I2 input2, I3 input3);

}
