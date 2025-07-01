package ai.agentscentral.core.convertors;

/**
 * TriConvertor
 *
 * @param <I1>
 * @param <I2>
 * @param <I3>
 * @param <O>
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface TriConvertor<I1, I2, I3, O> {

    O convert(I1 input1, I2 input2, I3 input3);

}
