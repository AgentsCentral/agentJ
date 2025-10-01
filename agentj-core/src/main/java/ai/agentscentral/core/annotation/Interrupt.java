package ai.agentscentral.core.annotation;

import ai.agentscentral.core.interrupt.InterruptType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Interrupt {


    InterruptType type() default InterruptType.CONFIRM;
    String rendererReference() default EMPTY;
    InterruptParameters[] parameters() default {};


}
