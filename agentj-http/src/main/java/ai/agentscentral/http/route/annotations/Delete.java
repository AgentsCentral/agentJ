package ai.agentscentral.http.route.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a controller method as an HTTP DELETE handler.
 *
 * <p>The optional {@link #path()} is appended to the controller's base path to form the
 * full route path.</p>
 *
 * @author Rizwan Idrees
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Delete {

    /**
     * Sub-path relative to the controller's base path; defaults to {@code ""} (the base
     * path itself).
     *
     * @return the sub-path for this DELETE handler
     */
    String path() default "";
}
