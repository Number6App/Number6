package dev.number6.comprehend

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.amazonaws.services.comprehend.model.DetectSentimentResult
import com.amazonaws.services.comprehend.model.SentimentType
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentTotals
import org.junit.jupiter.api.Test

internal class SentimentResultsToMessagePresentableSentimentTotalsTest {
    private var testee = SentimentResultsToMessageSentimentTotals()

    @Test
    fun sumSentimentTotals() {
        val results = listOf(
                DetectSentimentResult().withSentiment(SentimentType.MIXED),
                DetectSentimentResult().withSentiment(SentimentType.NEGATIVE),
                DetectSentimentResult().withSentiment(SentimentType.NEGATIVE),
                DetectSentimentResult().withSentiment(SentimentType.NEUTRAL),
                DetectSentimentResult().withSentiment(SentimentType.NEUTRAL),
                DetectSentimentResult().withSentiment(SentimentType.NEUTRAL),
                DetectSentimentResult().withSentiment(SentimentType.POSITIVE),
                DetectSentimentResult().withSentiment(SentimentType.POSITIVE),
                DetectSentimentResult().withSentiment(SentimentType.POSITIVE),
                DetectSentimentResult().withSentiment(SentimentType.POSITIVE))
        val totals = testee.apply(results)
        assertThat(totals).isNotNull()
        assertThat(totals[SentimentType.MIXED.toString()]).isEqualTo(1)
        assertThat(totals[SentimentType.NEGATIVE.toString()]).isEqualTo(2)
        assertThat(totals[SentimentType.NEUTRAL.toString()]).isEqualTo(3)
        assertThat(totals[SentimentType.POSITIVE.toString()]).isEqualTo(4)
    }
}