package dev.number6.generate;

import com.amazonaws.services.comprehend.model.SentimentScore;
import uk.org.fyodor.generators.Generator;

public class SentimentScoreGenerator implements Generator<SentimentScore> {

    @Override
    public SentimentScore next() {
        return new SentimentScore()
                .withMixed(CommonRDG.sentimentScoreFloat())
                .withNegative(CommonRDG.sentimentScoreFloat())
                .withPositive(CommonRDG.sentimentScoreFloat())
                .withNeutral(CommonRDG.sentimentScoreFloat());
    }
}
