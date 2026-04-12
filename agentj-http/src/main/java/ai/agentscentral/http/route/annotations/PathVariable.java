package ai.agentscentral.http.route.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Binds a controller method parameter (or field) to a URI path variable.
 *
 * <p>The {@link #name()} must match a {@code {variable}} placeholder in the route
 * path (e.g. {@code /users/{id}} with {@code @PathVariable("id")}).</p>
 *
 * @author Rizwan Idrees
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathVariable {

    /**
     * The name of the path variable as it appears in the route template.
     *
     * @return the path variable name
     */
    String name();
}
