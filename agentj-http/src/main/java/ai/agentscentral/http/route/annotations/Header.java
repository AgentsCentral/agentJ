package ai.agentscentral.http.route.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Binds a controller method parameter (or field) to a named HTTP request header.
 *
 * @author Rizwan Idrees
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Header {

    /**
     * The header name to read (case-insensitive lookup is the responsibility of the
     * binding layer).
     *
     * @return the header name
     */
    String name();

    /**
     * Whether the header must be present.  Defaults to {@code true}.
     *
     * @return {@code true} if the header is mandatory
     */
    boolean required() default true;
}
