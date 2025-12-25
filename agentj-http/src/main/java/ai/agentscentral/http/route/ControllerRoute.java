package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparingInt;

/**
 * ControllerRoute
 *
 * @author Rizwan Idrees
 */
public class ControllerRoute implements Route {

    public static final Comparator<ControllerMappedMatchedRoute> sortByPathLength = comparingInt(o -> o.path().length());
    private final Object controller;
    private final List<ControllerMappedRoute> mappedRoutes;

    public ControllerRoute(String path, Object controller) {
        this.controller = controller;
        this.mappedRoutes = ControllerMappedRoutesExtractor.extract(path, controller);
    }


    @Override
    public Optional<ControllerMatchedRoute> match(Request request) {
        return mappedRoutes.stream().flatMap(mr -> mr.match(request).stream()).max(sortByPathLength)
                 .map(mr -> new ControllerMatchedRoute(mr.path(), controller, mr));
    }
}
