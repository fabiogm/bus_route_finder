package busroutefinder.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class RouteDataFileParser {

    private BufferedReader reader;
    private long totalRoutes;
    private long remainingRoutes;

    public RouteDataFileParser(BufferedReader reader) throws IOException {
        this.reader = reader;
        totalRoutes = Integer.parseInt(reader.readLine());
        remainingRoutes = totalRoutes;
    }

    public Optional<Route> getNextRoute() throws IOException {
        String line = Optional.of(reader.readLine()).orElse("");
        List<Integer> routeTokens = stream(line.split(" ")).map(Integer::parseInt).collect(toList());

        if (routeTokens.isEmpty()) {
            return Optional.empty();
        }

        remainingRoutes--;
        return Optional.of(new Route(routeTokens.get(0), routeTokens.subList(1, routeTokens.size())));
    }

    /**
     * hasNext
     *
     * Checks if there's at least one more route to be read.
     *
     * Warning: thi number is based on what's informed in the file and so might not be accurate. You
     * should not assume the Optional<Route> is always filled.
     * @return
     */
    public boolean hasNext() {
        return remainingRoutes > 0;
    }

    public long getNumberOfRoutes() {
        return totalRoutes;
    }

}
