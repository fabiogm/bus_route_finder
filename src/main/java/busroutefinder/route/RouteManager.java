package busroutefinder.route;

import busroutefinder.parser.Leg;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class RouteManager {

    private Map<String, Set<Integer>> routes;

    public RouteManager() {
        routes = new HashMap<>();
    }

    public void addRoute(Integer id, Leg leg) {
        String legKey = leg.toString();

        if (!routes.containsKey(legKey)) {
            routes.put(legKey, new HashSet<>());
        }

        routes.get(legKey).add(id);
    }

    public Set<Integer> getRoute(String legKey) {
        return routes.get(legKey);
    }

}
