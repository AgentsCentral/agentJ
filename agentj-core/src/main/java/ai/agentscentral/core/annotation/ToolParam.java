package ai.agentscentral.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method parameter (or field) as a tool parameter whose value is supplied by the
 * LLM when it invokes the tool.
 *
 * <p>During tool discovery, {@link ai.agentscentral.core.tool.ToolBagToolsExtractor}
 * inspects each parameter of a {@link Tool}-annotated method. Parameters annotated with
 * {@code @ToolParam} are included in the tool's parameter schema that is sent to the model
 * provider, allowing the model to fill them in when calling the tool.</p>
 *
 * <p>Parameters that should be filled by a user interrupt response rather than by the
 * model must use {@link InterruptParam} instead.</p>
 *
 * @author Rizwan Idrees
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToolParam {

    /**
     * The parameter name sent to the model provider as part of the tool schema.
     *
     * @return the parameter name
     */
    String name();

    /**
     * Human-readable description of the parameter, sent to the model provider.
     *
     * <p>A clear description helps the model supply the correct value.</p>
     *
     * @return the parameter description
     */
    String description();

    /**
     * Whether this parameter must be provided by the model when invoking the tool.
     *
     * <p>Defaults to {@code true}.</p>
     *
     * @return {@code true} if the parameter is required; {@code false} if it is optional
     */
    boolean required() default true;
}
