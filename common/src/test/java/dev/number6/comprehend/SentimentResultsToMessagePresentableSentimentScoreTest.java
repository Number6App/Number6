package dev.number6.comprehend;

import dev.number6.comprehend.results.SentimentResultsToMessageSentimentScore;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import com.amazonaws.services.comprehend.model.SentimentScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static dev.number6.comprehend.results.SentimentResultsToMessageSentimentScore.Sentiment.*;
import static org.assertj.core.api.Assertions.assertThat;

class SentimentResultsToMessagePresentableSentimentScoreTest {

    private SentimentResultsToMessageSentimentScore testee;

    @BeforeEach
    void setup() {
        testee = new SentimentResultsToMessageSentimentScore();
    }

    @Test
    void sumsSentimentScores() {
        Collection<DetectSentimentResult> results = new ArrayList<>();
        results.add(new DetectSentimentResult().withSentimentScore(new SentimentScore().withMixed(1.1f).withNegative(2.2f).withNeutral(3.3f).withPositive(4.4f)));
        results.add(new DetectSentimentResult().withSentimentScore(new SentimentScore().withMixed(2.01f).withNegative(4.02f).withNeutral(6.03f).withPositive(8.04f)));
        results.add(new DetectSentimentResult().withSentimentScore(new SentimentScore().withMixed(3.001f).withNegative(6.002f).withNeutral(9.003f).withPositive(12.004f)));
        results.add(new DetectSentimentResult().withSentimentScore(new SentimentScore().withMixed(4.0001f).withNegative(8.0002f).withNeutral(12.0003f).withPositive(16.0004f)));
        results.add(new DetectSentimentResult().withSentimentScore(new SentimentScore().withMixed(5.00001f).withNegative(10.00002f).withNeutral(15.00003f).withPositive(20.00004f)));
        Map<String, Float> scores = testee.apply(results);
        assertThat(scores).isNotNull();
        assertThat(scores.get(MIXED.name())).isEqualTo(15.111111f);
        assertThat(scores.get(NEGATIVE.name())).isEqualTo(30.222221f);
        assertThat(scores.get(NEUTRAL.name())).isEqualTo(45.333331f);
        assertThat(scores.get(POSITIVE.name())).isEqualTo(60.444441f);
    }
}