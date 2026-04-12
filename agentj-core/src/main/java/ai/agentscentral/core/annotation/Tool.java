package ai.agentscentral.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method inside a {@link ai.agentscentral.core.tool.ToolBag} as a callable tool
 * that the LLM can invoke during a conversational turn.
 *
 * <p>At initialisation time, {@link ai.agentscentral.core.tool.ToolBagToolsExtractor}
 * reflects over all {@code ToolBag} instances registered on an agent and builds a
 * {@link ai.agentscentral.core.tool.ToolCall} descriptor for every method annotated with
 * {@code @Tool}. The descriptor — including name, description, and parameter metadata — is
 * sent to the model provider so the model can decide when and how to invoke the tool.</p>
 *
 * <p>Method parameters that the model supplies must be annotated with
 * {@link ToolParam}. Parameters that are filled by a user interrupt response instead must
 * be annotated with {@link InterruptParam}.</p>
 *
 * <p>Example:
 * <pre>{@code
 * public class WeatherTools implements ToolBag {
 *
 *     @Tool(name = "get_weather", description = "Returns current weather for a city")
 *     public String getWeather(@ToolParam(name = "city", description = "City name") String city) {
 *         ...
 *     }
 * }
 * }</pre>
 *
 * @author Rizwan Idrees
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Tool {

    /**
     * The tool name sent to the model provider.
     *
     * <p>Must be unique within all {@code ToolBag}s registered on the same agent.</p>
     *
     * @return the tool name
     */
    String name();

    /**
     * Human-readable description of what the tool does, sent to the model provider.
     *
     * <p>A clear, concise description improves the model's ability to decide when to
     * invoke the tool.</p>
     *
     * @return the tool description
     */
    String description();

    /**
     * Zero or more {@link Interrupt} declarations that are raised before this tool is
     * executed, prompting the user for confirmation or additional input.
     *
     * <p>Defaults to an empty {@link Interrupts} container (no interrupts).</p>
     *
     * @return the container holding all pre-call interrupt declarations
     */
    Interrupts interruptsBefore() default @Interrupts;
}
