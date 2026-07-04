import main.DepthFirstSearch;
import main.DirectedGraph;
import main.NGramMap;
import main.WordNetGraph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static com.google.common.truth.Truth.assertThat;

public class TestDirectedGraph {
    @Test
    public void testGraphOne() {
        HashMap<Integer, ArrayList<Integer>> testSet = new HashMap<>();
        ArrayList<Integer> edg0 = new ArrayList<>();
        edg0.add(1);
        edg0.add(2);
        ArrayList<Integer> edg1 = new ArrayList<>();
        edg1.add(2);
        ArrayList<Integer> edg2 = new ArrayList<>();
        ArrayList<Integer> edg3 = new ArrayList<>();
        edg3.add(0);
        edg3.add(1);
        edg3.add(2);
        testSet.put(0, edg0);
        testSet.put(1, edg1);
        testSet.put(2, edg2);
        testSet.put(3, edg3);
        DirectedGraph a = new DirectedGraph(testSet);
        assertThat(a.vertices()).containsExactly(0, 1, 2, 3);
        assertThat(a.edges(0)).containsExactly(1, 2);
        assertThat(a.edges(2)).containsExactly();

        a.addVertex(4);
        assertThat(a.edges(4)).containsExactly();
        a.addEdge(4, 2);
        assertThat(a.edges(4)).containsExactly(2);
        assertThat(a.interConnected()).isTrue();

        a.addEdge(4, 5);
        assertThat(a.interConnected()).isFalse();
    }
    @Test
    public void testGraphTwo() {
        HashMap<Integer, ArrayList<Integer>> testSet = new HashMap<>();
        ArrayList<Integer> edg0 = new ArrayList<>();
        edg0.add(1);
        edg0.add(2);
        ArrayList<Integer> edg1 = new ArrayList<>();
        edg1.add(2);
        ArrayList<Integer> edg2 = new ArrayList<>();
        ArrayList<Integer> edg3 = new ArrayList<>();
        edg3.add(0);
        edg3.add(1);
        edg3.add(2);
        testSet.put(0, edg0);
        testSet.put(1, edg1);
        testSet.put(2, edg2);
        testSet.put(3, edg3);
        NGramMap ngm = new NGramMap("./data/word_history_size14377.csv", "./data/year_history.csv");
        WordNetGraph a = new WordNetGraph("./data/synsets_size10.txt", "./data/hyponyms_size10.txt", ngm);
        DepthFirstSearch b = new DepthFirstSearch(a);
        assertThat(b.dfs(40607)).containsExactly(40607);
        b.reset();
        assertThat(b.dfs(35992)).containsExactly(35992, 5783, 39501, 43343, 80883);
        b.reset();
        assertThat(b.dfs(73801)).containsExactly(73801, 36289, 40607, 78590, 78880);
    }
}
