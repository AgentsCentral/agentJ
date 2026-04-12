package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;

import java.util.List;
import java.util.Optional;

import static ai.agentscentral.http.route.HttpRouter.sortByPathLength;

/**
 * {@link Route} implementation that discovers and registers all annotated HTTP handler
 * methods on a controller object.
 *
 * <p>At construction time, {@link ControllerMappedRoutesExtractor} reflects over the
 * controller's declared methods and builds a {@link ControllerMappedRoute} for each
 * method annotated with {@link ai.agentscentral.http.route.annotations.Get},
 * {@link ai.agentscentral.http.route.annotations.Post}, etc.  On each call to
 * {@link #match(Request)}, the best-matching mapped route is found and wrapped in a
 * {@link ControllerMatchedRoute}.</p>
 *
 * @author Rizwan Idrees
 */
public class ControllerRoute implements Route {


    private final Object controller;
    private final List<ControllerMappedRoute> mappedRoutes;

    /**
     * Creates a {@code ControllerRoute} that roots all controller paths under the given
     * base path.
     *
     * @param path       the base path prefix for all methods on this controller
     * @param controller the controller object whose annotated methods become routes
     */
    public ControllerRoute(String path, Object controller) {
        this.controller = controller;
        this.mappedRoutes = ControllerMappedRoutesExtractor.extract(path, controller);
    }


    @Override
    public Optional<MatchedRoute> match(Request request) {
        return mappedRoutes.stream().flatMap(mr -> mr.match(request).stream())
                .max(sortByPathLength)
                .map(mr -> new ControllerMatchedRoute(mr.path(), controller, (ControllerMappedMatchedRoute) mr));
    }
}
