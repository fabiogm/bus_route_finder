package busroutefinder.model;

import java.util.ArrayList;
import java.util.HashMap;
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
        Map<Integer, Boolean> visitedNodes = new HashMap<>();
        boolean shouldContinue = !shouldStop.test(source);

        q.add(source);
        visitedNodes.put(source, true);

        while ((!q.isEmpty()) && shouldContinue) {
            List<Integer> edges = adjacencyList.get(q.remove());

            for (Integer e : edges) {
                if (!visitedNodes.containsKey(e)) {
                    q.add(e);
                    visitedNodes.put(e, true);
                    if (shouldStop.test(e)) {
                        shouldContinue = false;
                        break;
                    }
                }
            }
        }

        return !shouldContinue;
    }
}
