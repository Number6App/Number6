package dev.number6.comprehend.results;

import com.amazonaws.services.comprehend.model.DetectSentimentResult;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public class PresentableSentimentResults extends ComprehensionResults<Collection<DetectSentimentResult>> {
    private final SentimentResultsToMessageSentimentScore sentimentResultsToMessageSentimentScore;
    private final SentimentResultsToMessageSentimentTotals sentimentResultsToMessageSentimentTotals;

    public PresentableSentimentResults(LocalDate comprehensionDate,
                                       Collection<DetectSentimentResult> sentimentResults,
                                       String channelName) {
        this(comprehensionDate, sentimentResults, channelName,
                new SentimentResultsToMessageSentimentScore(),
                new SentimentResultsToMessageSentimentTotals());
    }

    public PresentableSentimentResults(LocalDate comprehensionDate,
                                       Collection<DetectSentimentResult> sentimentResults,
                                       String channelName,
                                       SentimentResultsToMessageSentimentScore sentimentResultsToMessageSentimentScore,
                                       SentimentResultsToMessageSentimentTotals sentimentResultsToMessageSentimentTotals) {
        super(comprehensionDate, sentimentResults, channelName);
        this.sentimentResultsToMessageSentimentScore = sentimentResultsToMessageSentimentScore;
        this.sentimentResultsToMessageSentimentTotals = sentimentResultsToMessageSentimentTotals;
    }

    public Map<String, Integer> getSentimentTotals() {
        return sentimentResultsToMessageSentimentTotals.apply(getResults());
    }

    public Map<String, Float> getSentimentScoreTotals() {
        return sentimentResultsToMessageSentimentScore.apply(getResults());
    }

    @Override
    public String toString() {
        return "SentimentResults{" +
                "comprehensionDate=" + getComprehensionDate() +
                ", results=" + getResults() +
                ", channelName='" + getChannelName() + '\'' +
                '}';
    }
}
