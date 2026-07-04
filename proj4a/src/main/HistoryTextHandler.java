package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngrm;
    public HistoryTextHandler(NGramMap map) {
        ngrm = map;
    }
    @Override
    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        int startYear = query.startYear();
        int endYear = query.endYear();
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(word);
            result.append(": ");
            result.append(ngrm.weightHistory(word, startYear, endYear).toString());
            result.append("\n");
        }
        return result.toString();
    }
}
