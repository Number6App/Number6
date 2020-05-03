package dev.number6.generate

import com.amazonaws.services.comprehend.model.DetectSentimentResult
import com.amazonaws.services.comprehend.model.SentimentType
import uk.org.fyodor.generators.Generator
import uk.org.fyodor.generators.RDG

class DetectSentimentResultGenerator : Generator<DetectSentimentResult> {
    var sentimentScoreGenerator = SentimentScoreGenerator()
    var sentimentTypeGenerator = RDG.value(SentimentType::class.java)
    override fun next(): DetectSentimentResult {
        return DetectSentimentResult()
                .withSentiment(sentimentTypeGenerator.next())
                .withSentimentScore(sentimentScoreGenerator.next())
    }
}