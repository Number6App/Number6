package dev.number6.comprehend.results

import com.amazonaws.services.comprehend.model.DetectSentimentResult
import java.time.LocalDate

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