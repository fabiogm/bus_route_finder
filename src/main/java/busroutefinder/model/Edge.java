package busroutefinder.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Edge {

    private final Integer start;
    private final Integer end;

    public Edge(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public String toString() {
        return String.format("%s-%s", start, end);
    }
}
