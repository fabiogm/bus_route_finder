package busroutefinder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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

    public boolean areConnected(Integer start, Integer end) {
        Predicate<Integer> predicate = (e) -> e == end;

        return breadthFirstSearch(start, predicate);
    }

    public boolean pathTo(Integer start, Integer end, Stack<Edge> path) {
        return depthFirstSearch(start, path, e -> e == end);
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
     * depthFirstSearch
     *
     * Perform depth-first search on graph, starting from node source and keeping track of path until
     * shouldStop criteria is true.
     *
     * @param source - starting node of search
     * @param path - stack keeping track of path (set of edges) from source to last node reached when
     *             search is stopped.
     * @param shouldStop - predicate for early stoppage
     * @return true if search stopped before end of graph, otherwise return false.
     */
    public boolean depthFirstSearch(Integer source, Stack<Edge> path, Predicate<Integer> stoppagePredicate) {
        HashSet<Integer> visitedVertices = new HashSet<>();

        return depthFirstSearch(source, visitedVertices, path, stoppagePredicate);
    }

    public boolean depthFirstSearch(Integer source, HashSet<Integer> visitedVertices, Stack<Edge> path,
                                             Predicate<Integer> stoppagePredicate) {
        visitedVertices.add(source);

        if (stoppagePredicate.test(source)) {
            return true;
        }

        List<Integer> adjVertices = getNeighbourhood(source);

        for (Integer w : adjVertices) {
            if (!visitedVertices.contains(w)) {

                path.push(new Edge(source, w));

                boolean res = depthFirstSearch(w, visitedVertices, path, stoppagePredicate);

                if (res) {
                    return true;
                }

                path.pop();
            }
        }

        return false;
    }

    private List<Integer> getNeighbourhood(Integer vertex) {
        return adjacencyList.get(vertex);
    }
}
