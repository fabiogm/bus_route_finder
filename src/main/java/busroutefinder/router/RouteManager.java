package busroutefinder.router;

import busroutefinder.model.Edge;
import busroutefinder.model.Graph;
import busroutefinder.parser.Leg;
import busroutefinder.parser.Route;
import busroutefinder.parser.RouteDataFileParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class RouteManager {

    private RouteDataFileParser fileParser;
    private Graph routesGraph;

    public void loadRoutes(BufferedReader br) throws IOException {
        try {
            fileParser = new RouteDataFileParser(br);
        } catch (NumberFormatException e) {
            throw new IOException("Route data file format is invalid.");
        }

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
        return routesGraph.areConnected(source, destination);
    }

    private void addRoute(Integer id, Leg leg) {
        Edge edge = new Edge(leg.getSource(), leg.getDestination());
        routesGraph.addEdge(edge);
    }
}
