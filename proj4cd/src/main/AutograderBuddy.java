package main;

import browser.NgordnetQueryHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordHistoryFile, String yearHistoryFile,
            String synsetFile, String hyponymFile) {
        NGramMap ngm = new NGramMap(wordHistoryFile, yearHistoryFile);
        WordNetGraph wng = new WordNetGraph(synsetFile, hyponymFile, ngm);
        return new HyponymsHandler(wng);
    }
}
