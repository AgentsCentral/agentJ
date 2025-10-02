package ai.agentscentral.core.annotation;

import ai.agentscentral.core.interrupt.InterruptType;

import java.lang.annotation.*;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Interrupts.class)
public @interface Interrupt {

    InterruptType type() default InterruptType.CONFIRM;
    String rendererReference() default EMPTY;
    InterruptParameters[] parameters() default {};

}
