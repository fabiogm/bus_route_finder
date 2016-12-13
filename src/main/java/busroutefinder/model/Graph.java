package busroutefinder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Map<Integer, List<Integer>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(Edge v) {
        Integer first = v.getStart();
        Integer second = v.getEnd();

        if (!adjacencyList.containsKey(first)) {
            adjacencyList.put(first, new ArrayList<>());
        }

        if (!adjacencyList.containsKey(second)) {
            adjacencyList.put(second, new ArrayList<>());
        }

        adjacencyList.get(first).add(second);
        adjacencyList.get(second).add(first);
    }

    /**
     * enumeratePaths
     *
     * Enumerate all paths between two vertices in the graph using depth-first search.
     *
     * @param start - starting node of search
     * @param end - ending node of search
     * @param allPaths - list of paths (list of vertices) between start and end
     */
    public void enumeratePaths(Integer start, Integer end, List<List<Integer>> allPaths) {
        Set<Integer> visitedVertices = new HashSet<>();

        doEnumeratePaths(start, end, visitedVertices, allPaths);
    }

    private void doEnumeratePaths(Integer start, Integer end, Set<Integer> visitedVertices,
                                  List<List<Integer>> allPaths) {
        visitedVertices.add(start);

        if (start == end) {
            allPaths.add(new ArrayList<>(visitedVertices));
        } else {
            List<Integer> adjVertices = getNeighbourhood(start);

            for (Integer w : adjVertices) {
                if (!visitedVertices.contains(w)) {
                    doEnumeratePaths(w, end, visitedVertices, allPaths);
                }
            }
        }

        visitedVertices.remove(start);
    }

    private List<Integer> getNeighbourhood(Integer vertex) {
        return adjacencyList.get(vertex);
    }
}
