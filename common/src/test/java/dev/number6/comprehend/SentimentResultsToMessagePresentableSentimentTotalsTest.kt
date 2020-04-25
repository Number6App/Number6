package dev.number6.comprehend

import com.amazonaws.services.comprehend.model.DetectSentimentResult
import com.amazonaws.services.comprehend.model.SentimentType
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentTotals
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class SentimentResultsToMessagePresentableSentimentTotalsTest {
    private var testee: SentimentResultsToMessageSentimentTotals? = null

    @BeforeEach
    fun setup() {
        testee = SentimentResultsToMessageSentimentTotals()
    }

    @Test
    fun sumSentimentTotals() {
        val results: MutableCollection<DetectSentimentResult> = ArrayList()
        results.add(DetectSentimentResult().withSentiment(SentimentType.MIXED))
        results.add(DetectSentimentResult().withSentiment(SentimentType.NEGATIVE))
        results.add(DetectSentimentResult().withSentiment(SentimentType.NEGATIVE))
        results.add(DetectSentimentResult().withSentiment(SentimentType.NEUTRAL))
        results.add(DetectSentimentResult().withSentiment(SentimentType.NEUTRAL))
        results.add(DetectSentimentResult().withSentiment(SentimentType.NEUTRAL))
        results.add(DetectSentimentResult().withSentiment(SentimentType.POSITIVE))
        results.add(DetectSentimentResult().withSentiment(SentimentType.POSITIVE))
        results.add(DetectSentimentResult().withSentiment(SentimentType.POSITIVE))
        results.add(DetectSentimentResult().withSentiment(SentimentType.POSITIVE))
        val totals = testee?.apply(results)
        Assertions.assertThat(totals).isNotNull
        Assertions.assertThat(totals?.get(SentimentType.MIXED.toString())).isEqualTo(1)
        Assertions.assertThat(totals?.get(SentimentType.NEGATIVE.toString())).isEqualTo(2)
        Assertions.assertThat(totals?.get(SentimentType.NEUTRAL.toString())).isEqualTo(3)
        Assertions.assertThat(totals?.get(SentimentType.POSITIVE.toString())).isEqualTo(4)
    }
}