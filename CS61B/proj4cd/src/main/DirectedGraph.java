package main;

import java.util.ArrayList;
import java.util.HashMap;

public class DirectedGraph {
    private HashMap<Integer, ArrayList<Integer>> adjList;
    public DirectedGraph() {
        adjList = new HashMap<>();
    }
    public DirectedGraph(HashMap<Integer, ArrayList<Integer>> adj) {
        adjList = adj;
    }
    public ArrayList<Integer> vertices() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int key : adjList.keySet()) {
            result.add(key);
        }
        return result;
    }
    public ArrayList<Integer> edges(int x) {
        return adjList.get(x);
    }
    public void addVertex(int x) {
        adjList.put(x, new ArrayList<>());
    }
    public void addVertex(int x, ArrayList<Integer> edges) {
        adjList.put(x, edges);
    }
    public void addEdge(int x, int y) {
        ArrayList<Integer> newEdges = adjList.get(x);
        if (!newEdges.contains(y)) {
            newEdges.add(y);
        }
        adjList.put(x, newEdges);
    }
    //returns true if all vertices have either no neighbors or neighbors that are vertices
    //within the graph
    //otherwise, returns false
    public boolean interConnected() {
        ArrayList<Integer> verts = vertices();
        for (int vert: verts) {
            for (int neighbor: edges(vert)) {
                if (!verts.contains(neighbor)) {
                    return false;
                }
            }
        }
        return true;
    }
}
