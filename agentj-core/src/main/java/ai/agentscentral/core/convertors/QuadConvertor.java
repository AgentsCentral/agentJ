package ai.agentscentral.core.convertors;

/**
 * QuadConvertor
 *
 * @param <I1>
 * @param <I2>
 * @param <I3>
 * @param <O>
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface QuadConvertor<I1, I2, I3, I4, O> {

    O convert(I1 input1, I2 input2, I3 input3, I4 input4);
}
