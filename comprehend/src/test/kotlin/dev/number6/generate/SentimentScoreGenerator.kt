package dev.number6.generate

import com.amazonaws.services.comprehend.model.SentimentScore
import uk.org.fyodor.generators.Generator

class SentimentScoreGenerator : Generator<SentimentScore> {
    override fun next(): SentimentScore {
        return SentimentScore()
                .withMixed(ComprehendRDG.sentimentScoreFloat())
                .withNegative(ComprehendRDG.sentimentScoreFloat())
                .withPositive(ComprehendRDG.sentimentScoreFloat())
                .withNeutral(ComprehendRDG.sentimentScoreFloat())
    }
}