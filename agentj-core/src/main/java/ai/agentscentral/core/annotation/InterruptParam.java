package ai.agentscentral.core.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method parameter (or field) as one whose value is supplied by the user's
 * response to a pre-call {@link Interrupt}, rather than by the LLM.
 *
 * <p>When a {@link Tool}-annotated method defines one or more {@link Interrupt}s,
 * parameters annotated with {@code @InterruptParam} are excluded from the tool schema
 * sent to the model. Instead, their values are collected from the user's
 * {@link ai.agentscentral.core.session.message.UserInterruptPart} response and injected
 * into the method invocation by
 * {@link ai.agentscentral.core.tool.DefaultToolCallExecutor}.</p>
 *
 * <p>Parameters that the model should supply must use {@link ToolParam} instead.</p>
 *
 * @author Rizwan Idrees
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterruptParam {

    /**
     * The name of this interrupt parameter, used to match it with the corresponding
     * value in the user's interrupt response.
     *
     * @return the parameter name
     */
    String name();

    /**
     * Whether the user must provide a value for this parameter when responding to the
     * interrupt.
     *
     * <p>Defaults to {@code true}.</p>
     *
     * @return {@code true} if the parameter is required; {@code false} if it is optional
     */
    boolean required() default true;

    /**
     * A hint describing the expected type of the value, used by the interrupt UI renderer
     * to render an appropriate input control.
     *
     * @return the type hint string (e.g. {@code "string"}, {@code "boolean"})
     */
    String type();
}
