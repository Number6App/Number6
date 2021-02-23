package dev.number6.comprehend.adaptor

import com.amazonaws.AmazonWebServiceResult
import dev.number6.comprehend.AwsComprehendClient
import dev.number6.comprehend.port.ComprehensionPort
import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.message.ChannelMessages

class AwsComprehensionAdaptor(private val awsComprehendClient: AwsComprehendClient) : ComprehensionPort {

    override fun getEntitiesForSlackMessages(channelMessages: ChannelMessages): PresentableEntityResults {

        return PresentableEntityResults(channelMessages.comprehensionDate,
                getPresentableResults(channelMessages, awsComprehendClient::getMessageEntities),
                channelMessages.channelName)
    }

    override fun getSentimentForSlackMessages(channelMessages: ChannelMessages): PresentableSentimentResults {

        return PresentableSentimentResults(channelMessages.comprehensionDate,
                getPresentableResults(channelMessages, awsComprehendClient::getMessageSentiment),
                channelMessages.channelName)
    }

    override fun getKeyPhrasesForSlackMessages(channelMessages: ChannelMessages): PresentableKeyPhrasesResults {

        return PresentableKeyPhrasesResults(channelMessages.comprehensionDate,
                getPresentableResults(channelMessages, awsComprehendClient::getMessageKeyPhrases),
                channelMessages.channelName)
    }

    private fun <T : AmazonWebServiceResult<*>> getPresentableResults(
            channelMessages: ChannelMessages,
            resultFunction: (String?) -> T): Collection<T> {
        return getComprehendResultsForMessages(channelMessages, resultFunction)
    }

    private fun <T : AmazonWebServiceResult<*>> getComprehendResultsForMessages(
            channelMessages: ChannelMessages,
            resultFunction: (String?) -> T): Collection<T> {
        return channelMessages.messages
                .filter { m -> !m.isNullOrEmpty() }
                .map(resultFunction)
    }
}