package dev.number6.comprehend

import com.amazonaws.services.comprehend.model.DetectSentimentResult
import com.amazonaws.services.comprehend.model.SentimentScore
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentScore
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentScore.Sentiment
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class SentimentResultsToMessagePresentableSentimentScoreTest {
    private var testee: SentimentResultsToMessageSentimentScore? = null

    @BeforeEach
    fun setup() {
        testee = SentimentResultsToMessageSentimentScore()
    }

    @Test
    fun sumsSentimentScores() {
        val results: MutableCollection<DetectSentimentResult> = ArrayList()
        results.add(DetectSentimentResult().withSentimentScore(SentimentScore().withMixed(1.1f).withNegative(2.2f).withNeutral(3.3f).withPositive(4.4f)))
        results.add(DetectSentimentResult().withSentimentScore(SentimentScore().withMixed(2.01f).withNegative(4.02f).withNeutral(6.03f).withPositive(8.04f)))
        results.add(DetectSentimentResult().withSentimentScore(SentimentScore().withMixed(3.001f).withNegative(6.002f).withNeutral(9.003f).withPositive(12.004f)))
        results.add(DetectSentimentResult().withSentimentScore(SentimentScore().withMixed(4.0001f).withNegative(8.0002f).withNeutral(12.0003f).withPositive(16.0004f)))
        results.add(DetectSentimentResult().withSentimentScore(SentimentScore().withMixed(5.00001f).withNegative(10.00002f).withNeutral(15.00003f).withPositive(20.00004f)))
        val scores = testee?.apply(results)
        Assertions.assertThat(scores).isNotNull
        Assertions.assertThat(scores?.get(Sentiment.MIXED.name)).isEqualTo(15.111111f)
        Assertions.assertThat(scores?.get(Sentiment.NEGATIVE.name)).isEqualTo(30.222221f)
        Assertions.assertThat(scores?.get(Sentiment.NEUTRAL.name)).isEqualTo(45.333331f)
        Assertions.assertThat(scores?.get(Sentiment.POSITIVE.name)).isEqualTo(60.444441f)
    }
}