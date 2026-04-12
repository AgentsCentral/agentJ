package ai.agentscentral.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Container annotation for repeated {@link Interrupt} declarations on a
 * {@link Tool}-annotated method.
 *
 * <p>This annotation is not typically applied directly. The Java compiler uses it
 * automatically when multiple {@code @Interrupt} annotations are placed on the same
 * method. It is also used as the type of {@link Tool#interruptsBefore()}, where an
 * empty {@code @Interrupts} (no interrupts) serves as the default value.</p>
 *
 * @author Rizwan Idrees
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Interrupts {

    /**
     * The {@link Interrupt} declarations contained by this annotation.
     *
     * @return the array of interrupt declarations; defaults to an empty array
     */
    Interrupt[] value() default {};

}
