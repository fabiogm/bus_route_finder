package busroutefinder.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Route {
    private Integer id;
    private List<Integer> stations;
    private int p1;
    private int p2;

    public Route(Integer id, List<Integer> stations) throws IOException {
        if (stations.size() < 2) {
            throw new IOException("Routes must have at least two constituting stations.");
        }

        this.stations = new ArrayList<>(stations);
        this.id = id;
        p1 = 0;
        p2 = 1;
    }

    public Optional<Leg> getNextLeg() {
        if (!hasNextStationPair()) {
            return Optional.empty();
        }

        Integer[] stationPair = getNextStationPair();

        return Optional.of(new Leg(stationPair[0], stationPair[1]));
    }

    public boolean hasNextLeg() {
        return hasNextStationPair();
    }

    public Integer getId() {
        return id;
    }

    private Integer[] getNextStationPair() {
        Integer[] stationRet = new Integer[2];
        stationRet[0] = stations.get(p1);
        stationRet[1] = stations.get(p2);
        p1 = p2;
        p2 += 1;

        return stationRet;
    }

    private boolean hasNextStationPair() {
        return p2 < stations.size();
    }
}
