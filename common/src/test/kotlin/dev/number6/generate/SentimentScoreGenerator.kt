package dev.number6.generate

import com.amazonaws.services.comprehend.model.SentimentScore
import uk.org.fyodor.generators.Generator

class SentimentScoreGenerator : Generator<SentimentScore> {
    override fun next(): SentimentScore {
        return SentimentScore()
                .withMixed(CommonRDG.sentimentScoreFloat())
                .withNegative(CommonRDG.sentimentScoreFloat())
                .withPositive(CommonRDG.sentimentScoreFloat())
                .withNeutral(CommonRDG.sentimentScoreFloat())
    }
}