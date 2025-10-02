package ai.agentscentral.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Interrupts
 *
 * @author Rizwan Idrees
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Interrupts {

    Interrupt[] value() default {};

}
