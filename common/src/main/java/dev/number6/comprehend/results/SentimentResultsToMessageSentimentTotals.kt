package dev.number6.comprehend.results

import com.amazonaws.services.comprehend.model.DetectSentimentResult
import java.util.*
import java.util.function.Consumer
import java.util.function.Function

class SentimentResultsToMessageSentimentTotals : Function<Collection<DetectSentimentResult>, Map<String, Int>> {
    override fun apply(detectSentimentResults: Collection<DetectSentimentResult>): Map<String, Int> {
        val sentimentTotals: MutableMap<String, Int> = HashMap()
        detectSentimentResults!!.forEach(Consumer { s: DetectSentimentResult? -> sentimentTotals.merge(s!!.sentiment, 1) { i1: Int, i2: Int -> i1 + i2 } })
        return sentimentTotals
    }
}