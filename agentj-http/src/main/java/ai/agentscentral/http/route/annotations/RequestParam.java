package ai.agentscentral.http.route.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Binds a controller method parameter (or field) to a named HTTP query-string parameter.
 *
 * <p>The value is read from {@link ai.agentscentral.http.request.Request#parameters()}
 * using the specified {@link #name()} key and converted to the parameter's declared
 * type by {@link ai.agentscentral.http.route.TypeConvertor}.</p>
 *
 * @author Rizwan Idrees
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {

    /**
     * The query-string parameter name to bind.
     *
     * @return the parameter name
     */
    String name();

}
