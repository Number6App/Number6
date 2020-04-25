package dev.number6.comprehend.results

import com.amazonaws.services.comprehend.model.DetectSentimentResult
import java.util.*
import java.util.function.Function

class SentimentResultsToMessageSentimentScore : Function<Collection<DetectSentimentResult>, Map<String, Float>> {
    override fun apply(detectSentimentResults: Collection<DetectSentimentResult>): Map<String, Float> {
        val sentimentScoreTotals: MutableMap<String, Float> = HashMap()
        detectSentimentResults.forEach { s -> Arrays.stream(Sentiment.values()).forEach { sentiment: Sentiment -> sentimentScoreTotals.merge(sentiment.toString(), sentiment.getSentimentScore(s)) { f1: Float, f2: Float -> f1 + f2 } } }
        return sentimentScoreTotals
    }

    enum class Sentiment {
        MIXED {
            override fun getSentimentScore(result: DetectSentimentResult): Float {
                return result.sentimentScore.mixed
            }
        },
        POSITIVE {
            override fun getSentimentScore(result: DetectSentimentResult): Float {
                return result.sentimentScore.positive
            }
        },
        NEGATIVE {
            override fun getSentimentScore(result: DetectSentimentResult): Float {
                return result.sentimentScore.negative
            }
        },
        NEUTRAL {
            override fun getSentimentScore(result: DetectSentimentResult): Float {
                return result.sentimentScore.neutral
            }
        };

        abstract fun getSentimentScore(result: DetectSentimentResult): Float
    }
}