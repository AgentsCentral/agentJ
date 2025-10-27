package ai.agentscentral.core.annotation;

import ai.agentscentral.core.tool.NoPreInterruptCall;
import ai.agentscentral.core.tool.PreInterruptCall;

import java.lang.annotation.Repeatable;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Interrupt
 *
 * @author Rizwan Idrees
 */

@Repeatable(Interrupts.class)
public @interface Interrupt {

    String name();
    String rendererReference() default EMPTY;
    String[] params() default {};
    Class<? extends PreInterruptCall> preInterruptCall() default NoPreInterruptCall.class;

}
