package busroutefinder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Predicate;

public class Graph {
    private Map<Integer, List<Integer>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(Edge v) {
        Integer first = v.getFirst();
        Integer second = v.getSecond();

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

        return doBFS(start, predicate);
    }

    public boolean breadthFirstSearch(Integer source) {
        return doBFS(source, x -> true);
    }

    public boolean breadthFirstSearch(Integer source, Predicate<Integer> shouldStop) {
        return doBFS(source, shouldStop);
    }

    /**
     * doBFS
     *
     * Perform breadth-first search on graph, starting from node source.
     *
     * @param source - starting node of search
     * @param shouldStop - predicate for early stoppage
     * @return true if search stopped before end of graph, otherwise return false.
     */
    public boolean doBFS(Integer source, Predicate<Integer> shouldStop) {
        Queue<Integer> q = new LinkedList<>();
        HashSet<Integer> visitedNodes = new HashSet<>();
        boolean shouldContinue = !shouldStop.test(source);

        q.add(source);
        visitedNodes.add(source);

        while ((!q.isEmpty()) && shouldContinue) {
            List<Integer> adjVertices = getNeighbourhood(q.remove());

            for (Integer e : adjVertices) {
                if (!visitedNodes.contains(e)) {
                    q.add(e);
                    visitedNodes.add(e);
                    if (shouldStop.test(e)) {
                        shouldContinue = false;
                        break;
                    }
                }
            }
        }

        return !shouldContinue;
    }

    private List<Integer> getNeighbourhood(Integer vertex) {
        return adjacencyList.get(vertex);
    }
}
