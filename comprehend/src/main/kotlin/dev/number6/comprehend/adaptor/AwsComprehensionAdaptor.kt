package dev.number6.comprehend.adaptor

import com.amazonaws.AmazonWebServiceResult
import com.amazonaws.services.comprehend.model.DetectEntitiesResult
import com.amazonaws.services.comprehend.model.DetectKeyPhrasesResult
import com.amazonaws.services.comprehend.model.DetectSentimentResult
import dev.number6.comprehend.AwsComprehendClient
import dev.number6.comprehend.port.ComprehensionPort
import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.message.ChannelMessages

class AwsComprehensionAdaptor(private val awsComprehendClient: AwsComprehendClient) : ComprehensionPort {

    override fun getEntitiesForSlackMessages(channelMessages: ChannelMessages): PresentableEntityResults {

        return PresentableEntityResults(channelMessages.comprehensionDate,
                getPresentableResults(channelMessages, awsComprehendClient::getMessageEntities, ::mapEntityResults),
                channelMessages.channelName)
    }

    override fun getSentimentForSlackMessages(channelMessages: ChannelMessages): PresentableSentimentResults {

        return PresentableSentimentResults(channelMessages.comprehensionDate,
                getPresentableResults(channelMessages, awsComprehendClient::getMessageSentiment
                ) { c: Collection<DetectSentimentResult> -> c },
                channelMessages.channelName)
    }

    override fun getKeyPhrasesForSlackMessages(channelMessages: ChannelMessages): PresentableKeyPhrasesResults {

        return PresentableKeyPhrasesResults(channelMessages.comprehensionDate,
                getPresentableResults(channelMessages, awsComprehendClient::getMessageKeyPhrases, ::mapKeyPhrasesResults),
                channelMessages.channelName)
    }

    private fun <T : AmazonWebServiceResult<*>> getComprehendResultsForMessages(
            channelMessages: ChannelMessages,
            resultFunction: (String?) -> T): Collection<T> {
        return channelMessages.messages
                .filter { m -> !m.isNullOrEmpty() }
                .map(resultFunction)
    }

    private fun <T : AmazonWebServiceResult<*>, U> getPresentableResults(
            channelMessages: ChannelMessages,
            resultFunction: (String?) -> T,
            mapper: (Collection<T>) -> U): U {
        return mapper(getComprehendResultsForMessages(channelMessages, resultFunction))
    }

    private fun mapEntityResults(entityResults: Collection<DetectEntitiesResult>): Map<String, Map<String, Long>> {
        return entityResults
                .filter { e: DetectEntitiesResult -> !e.entities.isNullOrEmpty() }
                .flatMap { e: DetectEntitiesResult -> e.entities }
                .groupBy { e -> e.type }
                .mapValues { e -> e.value.groupBy { e1 -> e1.text } }
                .mapValues { e -> e.value.mapValues { e2 -> e2.value.size.toLong() } }
    }

    private fun mapKeyPhrasesResults(keyPhrasesResults: Collection<DetectKeyPhrasesResult>): Map<String, Long> {
        return keyPhrasesResults
                .filter { r: DetectKeyPhrasesResult -> !r.keyPhrases.isNullOrEmpty() }
                .flatMap { r: DetectKeyPhrasesResult -> r.keyPhrases }
                .groupBy { keyPhrase -> keyPhrase.text }
                .mapValues { e -> e.value.size.toLong() }
    }
}