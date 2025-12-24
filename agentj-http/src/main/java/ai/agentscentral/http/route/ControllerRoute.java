package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;

import java.util.List;
import java.util.Optional;

/**
 * ControllerRoute
 *
 * @author Rizwan Idrees
 */
public class ControllerRoute implements Route {

    private final String path;
    private final Object controller;
    private final List<ControllerMappedRoute> mappedRoutes;

    public ControllerRoute(String path, Object controller) {
        this.path = path;
        this.controller = controller;
        this.mappedRoutes = ControllerMappedRoutesExtractor.extract(path, controller);
    }


    @Override
    public Optional<ControllerMatchedRoute> match(Request request) {
        return Optional.empty();
    }
}
