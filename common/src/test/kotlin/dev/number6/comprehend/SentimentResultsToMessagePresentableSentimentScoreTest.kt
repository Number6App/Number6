package dev.number6.comprehend

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.amazonaws.services.comprehend.model.DetectSentimentResult
import com.amazonaws.services.comprehend.model.SentimentScore
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentScore
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentScore.Sentiment
import org.junit.jupiter.api.Test

internal class SentimentResultsToMessagePresentableSentimentScoreTest {
    private var testee = SentimentResultsToMessageSentimentScore()

    @Test
    fun sumsSentimentScores() {
        val results = listOf(
                DetectSentimentResult().withSentimentScore(SentimentScore().withMixed(1.1f).withNegative(2.2f).withNeutral(3.3f).withPositive(4.4f)),
                DetectSentimentResult().withSentimentScore(SentimentScore().withMixed(2.01f).withNegative(4.02f).withNeutral(6.03f).withPositive(8.04f)),
                DetectSentimentResult().withSentimentScore(SentimentScore().withMixed(3.001f).withNegative(6.002f).withNeutral(9.003f).withPositive(12.004f)),
                DetectSentimentResult().withSentimentScore(SentimentScore().withMixed(4.0001f).withNegative(8.0002f).withNeutral(12.0003f).withPositive(16.0004f)),
                DetectSentimentResult().withSentimentScore(SentimentScore().withMixed(5.00001f).withNegative(10.00002f).withNeutral(15.00003f).withPositive(20.00004f)))
        val scores = testee.apply(results)
        assertThat(scores).isNotNull()
        assertThat(scores[Sentiment.MIXED.name]).isEqualTo(15.111111f)
        assertThat(scores[Sentiment.NEGATIVE.name]).isEqualTo(30.222221f)
        assertThat(scores[Sentiment.NEUTRAL.name]).isEqualTo(45.333331f)
        assertThat(scores[Sentiment.POSITIVE.name]).isEqualTo(60.444441f)
    }
}