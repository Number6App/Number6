package dev.number6.comprehend.results;

import com.amazonaws.services.comprehend.model.DetectSentimentResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SentimentResultsToMessageSentimentTotals implements Function<Collection<DetectSentimentResult>, Map<String, Integer>> {

    @Override
    public Map<String, Integer> apply(Collection<DetectSentimentResult> detectSentimentResults) {
        Map<String, Integer> sentimentTotals = new HashMap<>();
        detectSentimentResults.forEach(s -> sentimentTotals.merge(s.getSentiment(), 1, (i1, i2) -> i1 + i2));
        return sentimentTotals;
    }
}
