package ai.agentscentral.http.route.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a controller method parameter (or field) as being bound from the HTTP request
 * body.
 *
 * <p>The body is deserialised by the configured
 * {@link ai.agentscentral.http.route.convertors.ContentConvertor}.</p>
 *
 * @author Rizwan Idrees
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Body {
}
