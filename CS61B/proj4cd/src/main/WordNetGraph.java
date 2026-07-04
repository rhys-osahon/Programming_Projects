package main;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordNetGraph extends DirectedGraph {
    HashMap<String, ArrayList<Integer>> nameToIds;
    HashMap<Integer, ArrayList<String>> idsToNames;
    NGramMap nameCounts;
    public WordNetGraph(String synsetFileName, String hyponymFileName, NGramMap ngm) {
        super();
        nameToIds = new HashMap<>();
        idsToNames = new HashMap<>();
        In synsetFile = new In(synsetFileName);
        In hyponymFile = new In(hyponymFileName);
        while (!synsetFile.isEmpty()) {
            String[] readLine = synsetFile.readLine().split(",");
            Integer id = Integer.parseInt(readLine[0]);
            String[] words = readLine[1].split(" ");
            ArrayList<String> names = new ArrayList<>();
            for (String word: words) {
                if (nameToIds.containsKey(word)) {
                    ArrayList<Integer> ids = nameToIds.get(word);
                    ids.add(id); // think don't have to put ids into HashMap as is reference type
                } else {
                    ArrayList<Integer> input = new ArrayList<>();
                    input.add(id);
                    nameToIds.put(word, input);
                }
                names.add(word);
            }
            idsToNames.put(id, names);
        }
        while (!hyponymFile.isEmpty()) {
            String[] readLine = hyponymFile.readLine().split(",");
            int parentId = Integer.parseInt(readLine[0]);
            ArrayList<Integer> childrenId = new ArrayList<>();
            for (int i = 1; i < readLine.length; i++) {
                childrenId.add(Integer.parseInt(readLine[i]));
            }
            addVertex(parentId, childrenId);
        }
        nameCounts = ngm;
    }
    public ArrayList<Integer> findIds(String word) {
        if (!nameToIds.containsKey(word)) {
            return new ArrayList<Integer>();
        }
        return nameToIds.get(word);
    }
    public ArrayList<String> findNames(int id) {
        if (!idsToNames.containsKey(id)) {
            return new ArrayList<String>();
        }
        return idsToNames.get(id);
    }
    public ArrayList<String> findHyponyms(String word) {
        ArrayList<Integer> synsetIds = findIds(word);
        ArrayList<Integer> hyponymIds = new ArrayList<>();
        DepthFirstSearch searcher = new DepthFirstSearch(this);
        for (int id: synsetIds) {
            unionList(hyponymIds, searcher.dfs(id));
            searcher.reset();
        }
        BST<String> hyponyms = hyponymContainer(hyponymIds);
        return hyponyms.inOrderList();
    }
    public ArrayList<String> findHyponyms(String word, int k, int startYear, int endYear) {
        ArrayList<String> hyponyms = findHyponyms(word);
        return kLogic(hyponyms, k, startYear, endYear);
    }
    public ArrayList<String> findHyponyms(List<String> words) {
        ArrayList<String> multiHyponyms = new ArrayList<String>();
        for (String word: words) {
            if (multiHyponyms.isEmpty()) {
                multiHyponyms = findHyponyms(word);
            } else {
                multiHyponyms = interceptList(multiHyponyms, findHyponyms(word));
                if (multiHyponyms.isEmpty()) {
                    return new ArrayList<>();
                }
            }
        }
        BST<String> orderedHyponyms = new BST<>();
        for (String hyponym: multiHyponyms) {
            orderedHyponyms.add(hyponym);
        }
        return orderedHyponyms.inOrderList();
    }
    public ArrayList<String> findHyponyms(List<String> words, int k, int startYear, int endYear) {
        ArrayList<String> hyponyms = findHyponyms(words);
        return kLogic(hyponyms, k, startYear, endYear);
    }
    private void unionList(ArrayList<Integer> a, ArrayList<Integer> b) {
        for (int y: b) {
            if (!a.contains(y)) {
                a.add(y);
            }
        }
    }
    private ArrayList<String> interceptList(ArrayList<String> a, ArrayList<String> b) {
        ArrayList<String> sum = new ArrayList<>();
        ArrayList<String> checker = a;
        ArrayList<String> checked = b;
        if (a.size() > b.size()) {
            checked = a;
            checker = b;
        }
        for (String x: checker) {
            if (checked.contains(x)) {
                sum.add(x);
            }
        }
        return sum;
    }
    private BST<String> hyponymContainer(ArrayList<Integer> ids) {
        BST<String> container = new BST<String>();
        for (int id: ids) {
            for (String hyponym: findNames(id)) {
                container.add(hyponym);
            }
        }
        return container;
    }
    private double overallCount(String word, int startYear, int endYear) {
        double sum = 0;
        TimeSeries ts = nameCounts.countHistory(word, startYear, endYear);
        for (double num: ts.data()) {
            sum += num;
        }
        return sum;
    }
    private ArrayList<String> kLogic(ArrayList<String> hyponyms, int k, int startYear, int endYear) {
        HashMap<String, Double> counts = new HashMap<>();
        BST<String> kHyponyms = new BST<>();
        for (String hyponym: hyponyms) {
            counts.put(hyponym, overallCount(hyponym, startYear, endYear));
        }
        int addCount = 0;
        ArrayList<String> passedHyponyms = new ArrayList<>();
        boolean zerosHit = false;
        while (addCount != k && passedHyponyms.size() != hyponyms.size() && !zerosHit) {
            double max = -1.0;
            String mWord = "";
            for (String hyponym: hyponyms) {
                if (!passedHyponyms.contains(hyponym)) {
                    if (max < counts.get(hyponym)) {
                        max = counts.get(hyponym);
                        mWord = hyponym;
                    }
                }
            }
            if (max == 0) {
                zerosHit = true;
            } else {
                kHyponyms.add(mWord);
                passedHyponyms.add(mWord);
                addCount++;
            }
        }
        return kHyponyms.inOrderList();
    }
}
/*private ArrayList<String> toEnglishPlease(ArrayList<Integer> a) {
        ArrayList<String> names = new ArrayList<>();
        for (int id: a) {
            for (String name: findNames(id)) {
                names = alphabeticalizeAdd(names, name);
            }
        }
        return names;
    }*/
/*private ArrayList<String> alphabeticalizeAdd(ArrayList<String> sum, String word) {
        if (sum.isEmpty()) {
            sum.add(word);
        } else {
            ArrayList<String> orderedNames = new ArrayList<>();
            boolean passed = false;
            int index = 0;
            while (index < sum.size()) {
                if (passed) {
                    orderedNames.add(sum.get(index));
                    index++;
                } else {
                    if (sum.get(index).compareTo(word) < 0) {
                        orderedNames.add(sum.get(index));
                        index++;
                    } else if (sum.get(index).compareTo(word) == 0) {
                        orderedNames.add(sum.get(index));
                        index++;
                        passed = true;
                    } else {
                        orderedNames.add(word);
                        passed = true;
                    }
                }
            }
            if (!passed) {
                orderedNames.add(word);
            }
            sum = orderedNames;
        }
        return sum;
    }*/
