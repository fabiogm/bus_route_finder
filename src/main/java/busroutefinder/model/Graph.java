package busroutefinder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;

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
     * breadthFirstSearch
     *
     * Perform breadth-first search on graph, starting from node source.
     *
     * @param source - starting node of search
     * @param shouldStop - predicate for early stoppage
     * @return true if search stopped before end of graph, otherwise return false.
     */
    public boolean breadthFirstSearch(Integer source, Predicate<Integer> shouldStop) {
        Queue<Integer> q = new LinkedList<>();
        HashSet<Integer> visitedVertices = new HashSet<>();
        boolean shouldContinue = !shouldStop.test(source);

        q.add(source);
        visitedVertices.add(source);

        while ((!q.isEmpty()) && shouldContinue) {
            List<Integer> adjVertices = getNeighbourhood(q.remove());

            for (Integer e : adjVertices) {
                if (!visitedVertices.contains(e)) {
                    q.add(e);
                    visitedVertices.add(e);
                    if (shouldStop.test(e)) {
                        shouldContinue = false;
                        break;
                    }
                }
            }
        }

        return !shouldContinue;
    }

    /**
     * doEnumeratePaths
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
