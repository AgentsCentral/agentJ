package ai.agentscentral.core.convertors;

/**
 * Convertor
 *
 * @param <I>
 * @param <O>
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface Convertor<I, O> {

    O convert(I input);

}
