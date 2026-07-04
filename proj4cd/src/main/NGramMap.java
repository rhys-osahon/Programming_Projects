package main;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;


/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    HashMap<String, TimeSeries> wordHistory;
    TimeSeries yearHistory;
    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        wordHistory = new HashMap<>();
        yearHistory = new TimeSeries();
        In wordHisFile = new In(wordHistoryFilename);
        In yearHisFile = new In(yearHistoryFilename);
        while (!wordHisFile.isEmpty()) {
            String[] readLine = wordHisFile.readLine().split("\t");
            String word = readLine[0];
            Integer year = Integer.parseInt(readLine[1]);
            Double bookMentions = Double.parseDouble(readLine[2]);
            if (wordHistory.containsKey(word)) {
                wordHistory.get(word).put(year, bookMentions);
            } else {
                TimeSeries ts = new TimeSeries();
                ts.put(year, bookMentions);
                wordHistory.put(word, ts);
            }
        }
        while (!yearHisFile.isEmpty()) {
            String[] readLine = yearHisFile.readLine().split(",");
            Integer year = Integer.parseInt(readLine[0]);
            Double textMentions = Double.parseDouble(readLine[1]);
            yearHistory.put(year, textMentions);
        }

    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        } else {
            return new TimeSeries(wordHistory.get(word), startYear, endYear);
        }
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        } else {
            return new TimeSeries(wordHistory.get(word), TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
        }
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(yearHistory, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        } else {
            return countHistory(word, startYear, endYear).dividedBy(yearHistory);
        }
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        } else {
            return countHistory(word).dividedBy(yearHistory);
        }
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sumWordCount = new TimeSeries();
        for (String word : words) {
            sumWordCount = sumWordCount.plus(countHistory(word, startYear, endYear));
        }
        return sumWordCount.dividedBy(yearHistory);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries sumWordCount = new TimeSeries();
        for (String word : words) {
            sumWordCount = sumWordCount.plus(countHistory(word, TimeSeries.MIN_YEAR, TimeSeries.MAX_YEAR));
        }
        return sumWordCount.dividedBy(yearHistory);
    }

}
