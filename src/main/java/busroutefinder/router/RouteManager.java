package busroutefinder.router;

import busroutefinder.input.RouteFileManager;
import busroutefinder.model.Edge;
import busroutefinder.model.Graph;
import busroutefinder.parser.Leg;
import busroutefinder.parser.Route;
import busroutefinder.parser.RouteDataFileParser;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

@Component
public class RouteManager {

    private RouteDataFileParser fileParser;

    private Graph routesGraph;

    private Map<String, Set<Integer>> routes;

    @Autowired
    public RouteManager(RouteFileManager fileManager) throws IOException {
        try {
            fileParser = new RouteDataFileParser(fileManager.getRouteFileContent());
        } catch (NumberFormatException e) {
            throw new IOException(format("Route data file %s does not exist or its format is invalid.",
                    fileManager.getFilename()));
        }

        routes = new HashMap<>();
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

    public boolean areConnected(Integer source, Integer destination) {
        List<List<Integer>> allPaths = new ArrayList<>();

        routesGraph.enumeratePaths(source, destination, allPaths);

        if (allPaths.isEmpty()) {
            return false;
        }

        return !findCommonRouteAmongPaths(allPaths).isEmpty();
    }

    private void addRoute(Integer id, Leg leg) {
        Edge edge = new Edge(leg.getSource(), leg.getDestination());
        routesGraph.addEdge(edge);

        String edgeKey = edge.toString();

        if (!routes.containsKey(edgeKey)) {
            routes.put(edgeKey, new HashSet<>());
        }

        routes.get(edge.toString()).add(id);
    }

    private Collection<Integer> findCommonRouteAmongPaths(List<List<Integer>> allPaths) {
        Collection<Integer> viableRoute = Collections.emptyList();

        for (List<Integer> path : allPaths) {
            List<Edge> edges = Edge.edgesFromVertices(path);

            viableRoute = findRouteForPath(edges);

            if (!viableRoute.isEmpty()) {
                break;
            }
        }

        return viableRoute;
    }

    private Collection<Integer> findRouteForPath(List<Edge> path) {
        HashSet<Integer> finalRoute = new HashSet<>();
        Set<Integer> routesForEdge = routes.get(path.get(0).toString());
        finalRoute.addAll(routesForEdge);

        for (Edge e : path) {
             routesForEdge = routes.get(e.toString());

             Collection<Integer> toRemove = new ArrayList<>(Sets.difference(finalRoute, routesForEdge));
             finalRoute.removeAll(toRemove);
        }

        return finalRoute;
    }

}
