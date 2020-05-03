package dev.number6.comprehend.adaptor

import com.amazonaws.services.comprehend.model.DetectEntitiesResult
import com.amazonaws.services.comprehend.model.DetectKeyPhrasesResult
import com.amazonaws.services.comprehend.model.DetectSentimentResult
import dev.number6.comprehend.AwsComprehendClient
import dev.number6.comprehend.port.ComprehensionPort
import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.message.ChannelMessages
import java.util.*

class AwsComprehensionAdaptor(private val awsComprehendClient: AwsComprehendClient) : ComprehensionPort {
    override fun getEntitiesForSlackMessages(channelMessages: ChannelMessages): PresentableEntityResults {
        val entityResults = channelMessages.messages
                .filter { m -> Objects.nonNull(m) }
                .filter { m -> m?.length!! > 0 }
                .map { message -> awsComprehendClient.getMessageEntities(message) }

        val entityCollection: Map<String, Map<String, Long>> = entityResults
                .filter { e: DetectEntitiesResult -> !e.entities.isNullOrEmpty() }
                .flatMap { e: DetectEntitiesResult -> e.entities }
                .groupBy { e -> e.type }
                .mapValues { e -> e.value.groupBy { e1 -> e1.text } }
                .mapValues { e -> e.value.mapValues { e2 -> e2.value.size.toLong() } }

        return PresentableEntityResults(channelMessages.comprehensionDate,
                entityCollection,
                channelMessages.channelName)
    }

    override fun getSentimentForSlackMessages(channelMessages: ChannelMessages): PresentableSentimentResults {
        val sentimentResults: Collection<DetectSentimentResult> = channelMessages.messages
                .filter { m -> !m.isNullOrEmpty() }
                .map { message -> awsComprehendClient.getMessageSentiment(message) }

        return PresentableSentimentResults(channelMessages.comprehensionDate,
                sentimentResults,
                channelMessages.channelName)
    }

    override fun getKeyPhrasesForSlackMessages(channelMessages: ChannelMessages): PresentableKeyPhrasesResults {
        val keyPhrasesResults: Collection<DetectKeyPhrasesResult> = channelMessages.messages
                .filter { m -> !m.isNullOrEmpty() }
                .map { message -> awsComprehendClient.getMessageKeyPhrases(message) }

        val keyPhrases: Map<String, Long> = keyPhrasesResults
                .filter { r: DetectKeyPhrasesResult -> !r.keyPhrases.isNullOrEmpty() }
                .flatMap { r: DetectKeyPhrasesResult -> r.keyPhrases }
                .groupBy { keyPhrase -> keyPhrase.text }
                .mapValues { e -> e.value.size.toLong() }

        return PresentableKeyPhrasesResults(channelMessages.comprehensionDate, keyPhrases, channelMessages.channelName)
    }

}