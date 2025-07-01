package ai.agentscentral.core.convertors;

/**
 * BiConvertor
 *
 * @param <I1>
 * @param <I2>
 * @param <O>
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface BiConvertor<I1, I2, O> {

    O convert(I1 input1, I2 input2);

}
