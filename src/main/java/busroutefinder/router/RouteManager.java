package busroutefinder.router;

import busroutefinder.input.RouteFileManager;
import busroutefinder.model.Edge;
import busroutefinder.model.Graph;
import busroutefinder.parser.Leg;
import busroutefinder.parser.Route;
import busroutefinder.parser.RouteDataFileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

import static java.lang.String.format;

@Component
public class RouteManager {

    private RouteDataFileParser fileParser;

    private Graph routesGraph;

    @Autowired
    public RouteManager(RouteFileManager fileManager) throws IOException {
        try {
            fileParser = new RouteDataFileParser(fileManager.getRouteFileContent());
        } catch (NumberFormatException e) {
            throw new IOException(format("Route data file %s does not exist or its format is invalid.",
                    fileManager.getFilename()));
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
