package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNetGraph wng;
    public HyponymsHandler(WordNetGraph graph) {
        wng = graph;
    }
    @Override
    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        int startYear = query.startYear();
        int endYear = query.endYear();
        int k = query.k();
        //will need to add if k == 0 and k != 0 during 4D
        //probably should put logic for k == 0 and k != 0 into their own functions
        if (k == 0) {
            if (words.size() == 1) {
                return wng.findHyponyms(words.getFirst()).toString();
            } else {
                return wng.findHyponyms(words).toString();
            }
        } else {
            if (words.size() == 1) {
                return wng.findHyponyms(words.getFirst(), k, startYear, endYear).toString();
            } else {
                return wng.findHyponyms(words, k, startYear, endYear).toString();
            }
        }
    }
}
