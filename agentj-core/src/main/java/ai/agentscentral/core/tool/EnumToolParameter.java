package ai.agentscentral.core.tool;

import java.util.Set;

/**
 * A {@link ToolParameter} for enum-typed parameters, additionally exposing the set of
 * valid constant values for inclusion in the tool schema.
 *
 * <p>Created by {@link ReflectionUtil#extractEnumParameter(int, java.lang.reflect.Parameter, ai.agentscentral.core.annotation.ToolParam)}
 * when {@link Class#isEnum()} returns {@code true} for the parameter type. The
 * {@code enumValues} set is sent to the model provider so the LLM is constrained to
 * valid enum constants.</p>
 *
 * @param index       zero-based position in the method's parameter list
 * @param type        the enum type
 * @param name        the parameter name from {@link ai.agentscentral.core.annotation.ToolParam#name()}
 * @param description the parameter description from {@link ai.agentscentral.core.annotation.ToolParam#description()}
 * @param required    whether the model must supply a value
 * @param jvmName     the JVM parameter name
 * @param enumValues  the string representations of all declared enum constants
 *
 * @author Rizwan Idrees
 */
public record EnumToolParameter(int index,
                                Class<?> type,
                                String name,
                                String description,
                                boolean required,
                                String jvmName,
                                Set<String> enumValues) implements ToolParameter {
}
