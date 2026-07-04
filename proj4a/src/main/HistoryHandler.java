package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.Plotter;
import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap ngrm;
    public HistoryHandler(NGramMap map) {
        ngrm = map;
    }
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        List<TimeSeries> plotTS = new ArrayList<>();
        for (String word : words) {
            plotTS.add(ngrm.weightHistory(word, startYear, endYear));
        }
        return Plotter.encodeChartAsString(Plotter.generateTimeSeriesChart(words, plotTS));
    }

}
