package dev.number6.comprehend.adaptor

import com.amazonaws.services.comprehend.model.DetectEntitiesResult
import com.amazonaws.services.comprehend.model.DetectKeyPhrasesResult
import com.amazonaws.services.comprehend.model.DetectSentimentResult
import com.amazonaws.services.comprehend.model.KeyPhrase
import dev.number6.comprehend.AwsComprehendClient
import dev.number6.comprehend.port.ComprehensionPort
import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableKeyPhrasesResults
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.message.ChannelMessages
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

class AwsComprehensionAdaptor(private val awsComprehendClient: AwsComprehendClient) : ComprehensionPort {
    override fun getEntitiesForSlackMessages(channelMessages: ChannelMessages): PresentableEntityResults {
        val entityResults: Collection<DetectEntitiesResult> = channelMessages.messages
                .filter { m -> Objects.nonNull(m) }
                .filter { m -> m?.length!! > 0 }
                .map { message -> awsComprehendClient.getMessageEntities(message) }

        val entityCollection: Map<String, Map<String, Long>> = entityResults
                .filter { e: DetectEntitiesResult -> e.entities != null && e.entities.size > 0 }
                .flatMap { e: DetectEntitiesResult -> e.entities }
                .groupBy { e -> e.type }
                .mapValues { e -> e.value.groupBy { e1 -> e1.text } }
                .mapValues { e -> e.value.mapValues { e2 -> e2.value.size.toLong() } }
//                .collect(Collectors.groupingBy(Function { obj: Entity -> obj.type }, Supplier { HashMap() }, Collectors.groupingBy({ obj: Entity -> obj.text }, Collectors.counting())))
        return PresentableEntityResults(channelMessages.comprehensionDate, entityCollection, channelMessages.channelName)
    }

    override fun getSentimentForSlackMessages(channelMessages: ChannelMessages): PresentableSentimentResults {
        val sentimentResults: Collection<DetectSentimentResult> = channelMessages.messages.stream()
                .filter { obj: String? -> Objects.nonNull(obj) }
                .filter { m: String? -> m!!.length > 0 }
                .map { message: String? -> awsComprehendClient.getMessageSentiment(message) }
                .collect(Collectors.toList())
        return PresentableSentimentResults(channelMessages.comprehensionDate,
                sentimentResults,
                channelMessages.channelName)
    }

    override fun getKeyPhrasesForSlackMessages(channelMessages: ChannelMessages): PresentableKeyPhrasesResults {
        val keyPhrasesResults: Collection<DetectKeyPhrasesResult> = channelMessages.messages.stream()
                .filter { obj: String? -> Objects.nonNull(obj) }
                .filter { m: String? -> m!!.length > 0 }
                .map { message: String? -> awsComprehendClient.getMessageKeyPhrases(message) }
                .collect(Collectors.toList())
        val keyPhrases = keyPhrasesResults.stream()
                .filter { r: DetectKeyPhrasesResult -> r.keyPhrases != null && r.keyPhrases.size > 0 }
                .flatMap { r: DetectKeyPhrasesResult -> r.keyPhrases.stream() }
                .collect(Collectors.groupingBy(Function { obj: KeyPhrase -> obj.text }, Collectors.counting()))
        return PresentableKeyPhrasesResults(channelMessages.comprehensionDate, keyPhrases, channelMessages.channelName)
    }

}