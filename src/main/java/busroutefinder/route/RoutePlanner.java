package busroutefinder.route;

import busroutefinder.input.RouteFileManager;
import busroutefinder.model.Edge;
import busroutefinder.model.Graph;
import busroutefinder.parser.Leg;
import busroutefinder.parser.Route;
import busroutefinder.parser.RouteDataFileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.google.common.collect.Sets.difference;
import static java.lang.String.format;

@Component
public class RoutePlanner {
    private RouteDataFileParser fileParser;
    private RouteManager routeManager;
    private Graph routesGraph;

    @Autowired
    public RoutePlanner(RouteFileManager fileManager, RouteManager routeManager) throws IOException {
        try {
            fileParser = new RouteDataFileParser(fileManager.getRouteFileContent());
        } catch (NumberFormatException e) {
            throw new IOException(format("Route data file %s does not exist or its format is invalid.",
                    fileManager.getFilename()));
        }

        this.routeManager = routeManager;
        routesGraph = new Graph();

        while (fileParser.hasNext()) {
            Optional<Route> nextRoute = fileParser.getNextRoute();

            if (nextRoute.isPresent()) {
                Route route = nextRoute.get();

                while (route.hasNextLeg()) {
                    addRoute(route.getId(), route.getNextLeg().get());
                }
            } else {
                break;
            }
        }
    }

    public boolean hasDirectBusRouteTo(Integer departure, Integer arrival) {

        if (departure == arrival) {
            return true;
        }

        List<List<Integer>> allPaths = new ArrayList<>();

        routesGraph.enumeratePaths(departure, arrival, allPaths);

        if (allPaths.isEmpty()) {
            return false;
        }

        return !getMatchedRoutes(allPaths).isEmpty();
    }

    private void addRoute(Integer id, Leg leg) {
        Edge edge = new Edge(leg.getSource(), leg.getDestination());

        routesGraph.addEdge(edge);
        routeManager.addRoute(id, leg);
    }

    /**
     *
     * getMatchedRoutes
     *
     * Tries to match routes with all given paths.
     *
     * @param allPaths - possible paths from depature to arrival
     *
     * @return A Collection of all routes that match at least one of the supplied paths.
     */
    private Collection<Integer> getMatchedRoutes(List<List<Integer>> allPaths) {
        Collection<Integer> possibleRoutes = Collections.emptyList();

        for (List<Integer> path : allPaths) {
            List<Edge> edges = Edge.edgesFromVertices(path);

            possibleRoutes = findRoutesForPath(edges);

            if (!possibleRoutes.isEmpty()) {
                break;
            }
        }

        return possibleRoutes;
    }

    /**
     *
     * findRoutesForPath
     *
     * Find all routes that travel the entire given path.
     *
     * @param path - the path
     *
     * @return a Collection of IDs of registered routes that travel the entire given path.
     */
    private Collection<Integer> findRoutesForPath(List<Edge> path) {
        HashSet<Integer> resultingRoute = new HashSet<>();
        Set<Integer> routesForEdge = routeManager.getRoute(path.get(0).toString());
        resultingRoute.addAll(routesForEdge);

        for (Edge e : path) {
            routesForEdge = routeManager.getRoute(e.toString());

             Collection<Integer> toRemove = new ArrayList<>(difference(resultingRoute, routesForEdge));
             resultingRoute.removeAll(toRemove);
        }

        return resultingRoute;
    }
}
