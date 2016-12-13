package busroutefinder.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class Edge {

    private final Integer start;
    private final Integer end;

    public Edge(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public static List<Edge> edgesFromVertices(List<Integer> vertices) {
        int pathLength = vertices.size();
        List<Edge> edges = new ArrayList<>();

        for (int i=0; i < pathLength - 1; i++) {
            edges.add(new Edge(vertices.get(i), vertices.get(i+1)));
        }

        return edges;
    }

    public String toString() {
        return String.format("%s-%s", start, end);
    }
}
