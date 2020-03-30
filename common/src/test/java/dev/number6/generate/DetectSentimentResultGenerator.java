package dev.number6.generate;


import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import com.amazonaws.services.comprehend.model.SentimentType;
import uk.org.fyodor.generators.Generator;

public class DetectSentimentResultGenerator implements Generator<DetectSentimentResult> {

    SentimentScoreGenerator sentimentScoreGenerator = new SentimentScoreGenerator();
    Generator<SentimentType> sentimentTypeGenerator = CommonRDG.value(SentimentType.class);

    @Override
    public DetectSentimentResult next() {
        return new DetectSentimentResult()
                .withSentiment(sentimentTypeGenerator.next())
                .withSentimentScore(sentimentScoreGenerator.next());
    }

}