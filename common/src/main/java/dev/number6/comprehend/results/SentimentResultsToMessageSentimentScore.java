package dev.number6.comprehend.results;

import com.amazonaws.services.comprehend.model.DetectSentimentResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class SentimentResultsToMessageSentimentScore implements Function<Collection<DetectSentimentResult>, Map<String, Float>> {

    @Override
    public Map<String, Float> apply(Collection<DetectSentimentResult> detectSentimentResults) {
        Map<String, Float> sentimentScoreTotals = new HashMap<>();
        detectSentimentResults.forEach(s -> Arrays.stream(Sentiment.values()).forEach(sentiment -> sentimentScoreTotals.merge(sentiment.toString(), sentiment.getSentimentScore(s), (f1, f2) -> f1 + f2)));
        return sentimentScoreTotals;
    }

    public enum Sentiment {
        MIXED {
            @Override
            Float getSentimentScore(DetectSentimentResult result) {
                return result.getSentimentScore().getMixed();
            }
        }, POSITIVE {
            @Override
            Float getSentimentScore(DetectSentimentResult result) {
                return result.getSentimentScore().getPositive();
            }
        }, NEGATIVE {
            @Override
            Float getSentimentScore(DetectSentimentResult result) {
                return result.getSentimentScore().getNegative();
            }
        }, NEUTRAL {
            @Override
            Float getSentimentScore(DetectSentimentResult result) {
                return result.getSentimentScore().getNeutral();
            }
        };

        abstract Float getSentimentScore(DetectSentimentResult result);
    }
}
