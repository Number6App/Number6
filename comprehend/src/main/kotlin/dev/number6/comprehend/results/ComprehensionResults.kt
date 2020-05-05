package dev.number6.comprehend.results

import com.amazonaws.AmazonWebServiceResult
import com.amazonaws.services.comprehend.model.DetectEntitiesResult
import com.amazonaws.services.comprehend.model.DetectKeyPhrasesResult
import com.amazonaws.services.comprehend.model.DetectSentimentResult
import java.time.LocalDate

sealed class ComprehensionResults<T : AmazonWebServiceResult<*>, U>(val comprehensionDate: LocalDate, val results: Collection<T>, val channelName: String) {

    abstract val presentableResults: U
}


class PresentableEntityResults(comprehensionDate: LocalDate,
                               entitiesResults: Collection<DetectEntitiesResult>,
                               channelName: String) :
        ComprehensionResults<DetectEntitiesResult, Map<String, Map<String, Long>>>(comprehensionDate, entitiesResults, channelName) {

    override val presentableResults: Map<String, Map<String, Long>> by lazy {
        results.filter { e: DetectEntitiesResult -> !e.entities.isNullOrEmpty() }
                .flatMap { e: DetectEntitiesResult -> e.entities }
                .groupBy { e -> e.type }
                .mapValues { e -> e.value.groupBy { e1 -> e1.text } }
                .mapValues { e -> e.value.mapValues { e2 -> e2.value.size.toLong() } }
    }

    override fun toString(): String {
        return "EntityResults{" +
                "comprehensionDate=" + comprehensionDate +
                ", entitiesResults=" + results +
                ", channelName='" + channelName + '\'' +
                '}'
    }
}

class PresentableKeyPhrasesResults(comprehensionDate: LocalDate,
                                   keyPhrasesResults: Collection<DetectKeyPhrasesResult>,
                                   channelName: String) :
        ComprehensionResults<DetectKeyPhrasesResult, Map<String, Long>>(comprehensionDate, keyPhrasesResults, channelName) {
    override fun toString(): String {
        return "KeyPhrasesResults{" +
                "comprehensionDate=" + comprehensionDate +
                ", keyPhrasesResults=" + results +
                ", channelName='" + channelName + '\'' +
                '}'
    }

    override val presentableResults: Map<String, Long> by lazy {
        results
                .filter { r: DetectKeyPhrasesResult -> !r.keyPhrases.isNullOrEmpty() }
                .flatMap { r: DetectKeyPhrasesResult -> r.keyPhrases }
                .groupBy { keyPhrase -> keyPhrase.text }
                .mapValues { e -> e.value.size.toLong() }
    }
}

class PresentableSentimentResults @JvmOverloads constructor(comprehensionDate: LocalDate,
                                                            sentimentResults: Collection<DetectSentimentResult>,
                                                            channelName: String,
                                                            private val sentimentResultsToMessageSentimentScore: SentimentResultsToMessageSentimentScore =
                                                                    SentimentResultsToMessageSentimentScore(),
                                                            private val sentimentResultsToMessageSentimentTotals: SentimentResultsToMessageSentimentTotals =
                                                                    SentimentResultsToMessageSentimentTotals()) :
        ComprehensionResults<DetectSentimentResult, Collection<DetectSentimentResult>>(comprehensionDate, sentimentResults, channelName) {
    val sentimentTotals: Map<String, Int>
        get() = sentimentResultsToMessageSentimentTotals.apply(results)

    val sentimentScoreTotals: Map<String, Float>
        get() = sentimentResultsToMessageSentimentScore.apply(results)

    override val presentableResults: Collection<DetectSentimentResult> by lazy {
        results
    }

    override fun toString(): String {
        return "SentimentResults{" +
                "comprehensionDate=" + comprehensionDate +
                ", results=" + results +
                ", channelName='" + channelName + '\'' +
                '}'
    }
}