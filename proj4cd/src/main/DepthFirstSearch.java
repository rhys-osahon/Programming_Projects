package main;

import java.util.*;

public class DepthFirstSearch {
    private WordNetGraph travGraph;
    private HashMap<Integer, Boolean> marked;
    public DepthFirstSearch(WordNetGraph gr) {
        travGraph = gr;
        marked = new HashMap<>();
        for (int x: travGraph.idsToNames.keySet()) {
            marked.put(x, false);
        }
    }
    // post order dfs
    public ArrayList<Integer> dfs(int start) {
        if (marked.get(start)) {
            return new ArrayList<>();
        }
        ArrayList<Integer> output = new ArrayList<>();
        marked.put(start, true);
        if (travGraph.edges(start) != null) {
            for (int x : travGraph.edges(start)) {
                for (int y : dfs(x)) {
                    if (!output.contains(y)) {
                        output.add(y);
                    }
                }
            }
        }
        output.add(start);
        return output;
    }
    public void reset() {
        for (int x: travGraph.idsToNames.keySet()) {
            marked.put(x, false);
        }
    }
}
