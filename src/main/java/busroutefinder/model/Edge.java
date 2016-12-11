package busroutefinder.model;

import lombok.Getter;

@Getter
public class Edge {

    private final Integer start;
    private final Integer end;

    public Edge(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }
}
