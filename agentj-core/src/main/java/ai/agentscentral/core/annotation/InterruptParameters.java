package ai.agentscentral.core.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * InterruptParameters
 *
 * @author Rizwan Idrees
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InterruptParameters {

    String name();

    String value();
}
