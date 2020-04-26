package dev.number6.comprehend.results

import com.amazonaws.services.comprehend.model.DetectSentimentResult
import java.time.LocalDate

sealed class ComprehensionResults<T>(val comprehensionDate: LocalDate, val results: T, val channelName: String)

class PresentableEntityResults(comprehensionDate: LocalDate,
                               entitiesResults: Map<String, Map<String, Long>>,
                               channelName: String) :
        ComprehensionResults<Map<String, Map<String, Long>>>(comprehensionDate, entitiesResults, channelName) {
    override fun toString(): String {
        return "EntityResults{" +
                "comprehensionDate=" + comprehensionDate +
                ", entitiesResults=" + results +
                ", channelName='" + channelName + '\'' +
                '}'
    }
}

class PresentableKeyPhrasesResults(comprehensionDate: LocalDate,
                                   keyPhrasesResults: Map<String, Long>,
                                   channelName: String) :
        ComprehensionResults<Map<String, Long>>(comprehensionDate, keyPhrasesResults, channelName) {
    override fun toString(): String {
        return "KeyPhrasesResults{" +
                "comprehensionDate=" + comprehensionDate +
                ", keyPhrasesResults=" + results +
                ", channelName='" + channelName + '\'' +
                '}'
    }
}

class PresentableSentimentResults @JvmOverloads constructor(comprehensionDate: LocalDate,
                                                            sentimentResults: Collection<DetectSentimentResult>,
                                                            channelName: String,
                                                            private val sentimentResultsToMessageSentimentScore: SentimentResultsToMessageSentimentScore =
                                                                    SentimentResultsToMessageSentimentScore(),
                                                            private val sentimentResultsToMessageSentimentTotals: SentimentResultsToMessageSentimentTotals =
                                                                    SentimentResultsToMessageSentimentTotals()) :
        ComprehensionResults<Collection<DetectSentimentResult>>(comprehensionDate, sentimentResults, channelName) {
    val sentimentTotals: Map<String, Int>
        get() = sentimentResultsToMessageSentimentTotals.apply(results)

    val sentimentScoreTotals: Map<String, Float>
        get() = sentimentResultsToMessageSentimentScore.apply(results)

    override fun toString(): String {
        return "SentimentResults{" +
                "comprehensionDate=" + comprehensionDate +
                ", results=" + results +
                ", channelName='" + channelName + '\'' +
                '}'
    }

}