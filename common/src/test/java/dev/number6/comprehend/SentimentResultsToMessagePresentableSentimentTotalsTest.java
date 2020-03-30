package dev.number6.comprehend;

import dev.number6.comprehend.results.SentimentResultsToMessageSentimentTotals;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import com.amazonaws.services.comprehend.model.SentimentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SentimentResultsToMessagePresentableSentimentTotalsTest {

    private SentimentResultsToMessageSentimentTotals testee;

    @BeforeEach
    void setup() {
        testee = new SentimentResultsToMessageSentimentTotals();
    }

    @Test
    void sumSentimentTotals(){

        Collection<DetectSentimentResult> results = new ArrayList<>();
        results.add(new DetectSentimentResult().withSentiment(SentimentType.MIXED));
        results.add(new DetectSentimentResult().withSentiment(SentimentType.NEGATIVE));
        results.add(new DetectSentimentResult().withSentiment(SentimentType.NEGATIVE));
        results.add(new DetectSentimentResult().withSentiment(SentimentType.NEUTRAL));
        results.add(new DetectSentimentResult().withSentiment(SentimentType.NEUTRAL));
        results.add(new DetectSentimentResult().withSentiment(SentimentType.NEUTRAL));
        results.add(new DetectSentimentResult().withSentiment(SentimentType.POSITIVE));
        results.add(new DetectSentimentResult().withSentiment(SentimentType.POSITIVE));
        results.add(new DetectSentimentResult().withSentiment(SentimentType.POSITIVE));
        results.add(new DetectSentimentResult().withSentiment(SentimentType.POSITIVE));
        Map<String, Integer> totals = testee.apply(results);

        assertThat(totals).isNotNull();
        assertThat(totals.get(SentimentType.MIXED.toString())).isEqualTo(1);
        assertThat(totals.get(SentimentType.NEGATIVE.toString())).isEqualTo(2);
        assertThat(totals.get(SentimentType.NEUTRAL.toString())).isEqualTo(3);
        assertThat(totals.get(SentimentType.POSITIVE.toString())).isEqualTo(4);
    }
}