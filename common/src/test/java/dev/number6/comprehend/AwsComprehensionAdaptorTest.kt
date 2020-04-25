package dev.number6.comprehend

import com.amazonaws.services.comprehend.model.*
import dev.number6.comprehend.AwsComprehendClient
import dev.number6.comprehend.adaptor.AwsComprehensionAdaptor
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentScore
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentTotals
import dev.number6.message.ChannelMessages
import org.assertj.core.api.Assertions
import org.assertj.core.data.Offset
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import uk.org.fyodor.generators.RDG
import java.time.LocalDate
import java.util.*

@Disabled("replace Mockito")
internal class AwsComprehensionAdaptorTest {
    private val client = Mockito.mock(AwsComprehendClient::class.java)
    private val sentimentResultsToMessageSentimentScore = Mockito.mock(SentimentResultsToMessageSentimentScore::class.java)
    private val sentimentResultsToMessageSentimentTotals = Mockito.mock(SentimentResultsToMessageSentimentTotals::class.java)
    private var messageEntityResults: MutableMap<String, DetectEntitiesResult>? = null
    private var messageKeyPhrasesResults: MutableMap<String, DetectKeyPhrasesResult>? = null
    private var messageSentimentResults: MutableMap<String, DetectSentimentResult>? = null
    private var testee: AwsComprehensionAdaptor? = null

    @BeforeEach
    fun setup() {
        Mockito.`when`(client.getMessageEntities(ArgumentMatchers.any())).thenAnswer { i: InvocationOnMock -> messageEntityResults?.get(i.getArgument<Any>(0)) }
        Mockito.`when`(client.getMessageKeyPhrases(ArgumentMatchers.any())).thenAnswer { i: InvocationOnMock -> messageKeyPhrasesResults?.get(i.getArgument<Any>(0)) }
        Mockito.`when`(client.getMessageSentiment(ArgumentMatchers.any())).thenAnswer { i: InvocationOnMock -> messageSentimentResults?.get(i.getArgument<Any>(0)) }
        testee = AwsComprehensionAdaptor(client)
    }

    @BeforeEach
    fun setMessageSentimentScores() {
        val sentimentScores: MutableMap<String, Float> = HashMap()
        sentimentScores[SentimentType.MIXED.toString()] = 1.1f
        sentimentScores[SentimentType.POSITIVE.toString()] = 2.2f
        sentimentScores[SentimentType.NEUTRAL.toString()] = 3.3f
        sentimentScores[SentimentType.NEGATIVE.toString()] = 4.4f
        val sentimentTotals: MutableMap<String, Int> = HashMap()
        sentimentTotals[SentimentType.MIXED.toString()] = 10
        sentimentTotals[SentimentType.POSITIVE.toString()] = 20
        sentimentTotals[SentimentType.NEUTRAL.toString()] = 30
        sentimentTotals[SentimentType.NEGATIVE.toString()] = 40
        Mockito.`when`(sentimentResultsToMessageSentimentScore.apply(ArgumentMatchers.any())).thenReturn(sentimentScores)
        Mockito.`when`(sentimentResultsToMessageSentimentTotals.apply(ArgumentMatchers.any())).thenReturn(sentimentTotals)
    }

    @BeforeEach
    fun setMessageKeyPhrasesResults() {
        messageKeyPhrasesResults = HashMap()
        (messageKeyPhrasesResults as HashMap<String, DetectKeyPhrasesResult>)[MESSAGE_1_TEXT] = DetectKeyPhrasesResult().withKeyPhrases(KeyPhrase().withText(KEY_PHRASE_TEXT_1))
        (messageKeyPhrasesResults as HashMap<String, DetectKeyPhrasesResult>)[MESSAGE_2_TEXT] = DetectKeyPhrasesResult().withKeyPhrases(KeyPhrase().withText(KEY_PHRASE_TEXT_2),
                KeyPhrase().withText(KEY_PHRASE_TEXT_1))
        (messageKeyPhrasesResults as HashMap<String, DetectKeyPhrasesResult>)[MESSAGE_3_TEXT] = DetectKeyPhrasesResult().withKeyPhrases(KeyPhrase().withText(KEY_PHRASE_TEXT_3),
                KeyPhrase().withText(KEY_PHRASE_TEXT_2),
                KeyPhrase().withText(KEY_PHRASE_TEXT_1))
    }

    @BeforeEach
    fun setMessageEntityResults() {
        messageEntityResults = HashMap()
        (messageEntityResults as HashMap<String, DetectEntitiesResult>)[MESSAGE_1_TEXT] = DetectEntitiesResult().withEntities(Entity().withText(COMMERCIAL_ITEM_ENTITY).withType(EntityType.COMMERCIAL_ITEM))
        (messageEntityResults as HashMap<String, DetectEntitiesResult>)[MESSAGE_2_TEXT] = DetectEntitiesResult().withEntities(Entity().withText(COMMERCIAL_ITEM_ENTITY).withType(EntityType.COMMERCIAL_ITEM),
                Entity().withText(EVENT_ENTITY_1).withType(EntityType.EVENT))
        (messageEntityResults as HashMap<String, DetectEntitiesResult>)[MESSAGE_3_TEXT] = DetectEntitiesResult().withEntities(Entity().withText(COMMERCIAL_ITEM_ENTITY).withType(EntityType.COMMERCIAL_ITEM),
                Entity().withText(EVENT_ENTITY_1).withType(EntityType.EVENT),
                Entity().withText(EVENT_ENTITY_2).withType(EntityType.EVENT),
                Entity().withText(ORGANIZATION_ENTITY).withType(EntityType.ORGANIZATION)
        )
    }

    @BeforeEach
    fun setMessageSentimentResults() {
        messageSentimentResults = HashMap()
        (messageSentimentResults as HashMap<String, DetectSentimentResult>)[MESSAGE_1_TEXT] = DetectSentimentResult()
                .withSentiment(SentimentType.NEUTRAL)
                .withSentimentScore(SentimentScore().withMixed(0.1f).withNegative(0.1f).withNeutral(0.1f).withPositive(0.1f))
        (messageSentimentResults as HashMap<String, DetectSentimentResult>)[MESSAGE_2_TEXT] = DetectSentimentResult()
                .withSentiment(SentimentType.NEUTRAL)
                .withSentimentScore(SentimentScore().withMixed(0.2f).withNegative(0.1f).withNeutral(0.2f).withPositive(0.2f))
        (messageSentimentResults as HashMap<String, DetectSentimentResult>)[MESSAGE_3_TEXT] = DetectSentimentResult()
                .withSentiment(SentimentType.NEUTRAL)
                .withSentimentScore(SentimentScore().withMixed(0.3f).withNegative(0.2f).withNeutral(0.3f).withPositive(0.3f))
        (messageSentimentResults as HashMap<String, DetectSentimentResult>)[MESSAGE_4_TEXT] = DetectSentimentResult()
                .withSentiment(SentimentType.MIXED)
                .withSentimentScore(SentimentScore().withMixed(0.4f).withNegative(0.3f).withNeutral(0.3f).withPositive(0.4f))
        (messageSentimentResults as HashMap<String, DetectSentimentResult>)[MESSAGE_5_TEXT] = DetectSentimentResult()
                .withSentiment(SentimentType.MIXED)
                .withSentimentScore(SentimentScore().withMixed(0.5f).withNegative(0.5f).withNeutral(0.4f).withPositive(0.5f))
        (messageSentimentResults as HashMap<String, DetectSentimentResult>)[MESSAGE_6_TEXT] = DetectSentimentResult()
                .withSentiment(SentimentType.POSITIVE)
                .withSentimentScore(SentimentScore().withMixed(0.5f).withNegative(0.6f).withNeutral(0.6f).withPositive(0.6f))
    }

    @Test
    fun getsEntities() {
        val messages: Collection<String?> = Arrays.asList(MESSAGE_1_TEXT, MESSAGE_2_TEXT, MESSAGE_3_TEXT)
        val channelName = RDG.string(20).next()
        val channelMessages = ChannelMessages(channelName, messages, LocalDate.now())
        val results = testee?.getEntitiesForSlackMessages(channelMessages)
        Assertions.assertThat(results?.channelName).isEqualTo(channelName)
        Assertions.assertThat(results?.comprehensionDate).isEqualTo(LocalDate.now())
        Assertions.assertThat(results?.results).hasSize(3)
        Assertions.assertThat(results?.results?.get(EntityType.COMMERCIAL_ITEM.toString())).hasSize(1)
        Assertions.assertThat(results?.results?.get(EntityType.COMMERCIAL_ITEM.toString())?.get(COMMERCIAL_ITEM_ENTITY)).isEqualTo(3)
        Assertions.assertThat(results?.results?.get(EntityType.EVENT.toString())).hasSize(2)
        Assertions.assertThat(results?.results?.get(EntityType.EVENT.toString())?.get(EVENT_ENTITY_1)).isEqualTo(2)
        Assertions.assertThat(results?.results?.get(EntityType.EVENT.toString())?.get(EVENT_ENTITY_2)).isEqualTo(1)
        Assertions.assertThat(results?.results?.get(EntityType.ORGANIZATION.toString())).hasSize(1)
        Assertions.assertThat(results?.results?.get(EntityType.ORGANIZATION.toString())?.get(ORGANIZATION_ENTITY)).isEqualTo(1)
    }

    @Test
    fun getsKeyPhrases() {
        val messages: Collection<String?> = Arrays.asList(MESSAGE_1_TEXT, MESSAGE_2_TEXT, MESSAGE_3_TEXT)
        val channelName = RDG.string(20).next()
        val channelMessages = ChannelMessages(channelName, messages, LocalDate.now())
        val results = testee?.getKeyPhrasesForSlackMessages(channelMessages)
        Assertions.assertThat(results?.channelName).isEqualTo(channelName)
        Assertions.assertThat(results?.comprehensionDate).isEqualTo(LocalDate.now())
        Assertions.assertThat(results?.results).hasSize(3)
        Assertions.assertThat(results?.results?.get(KEY_PHRASE_TEXT_1)).isEqualTo(3)
        Assertions.assertThat(results?.results?.get(KEY_PHRASE_TEXT_2)).isEqualTo(2)
        Assertions.assertThat(results?.results?.get(KEY_PHRASE_TEXT_3)).isEqualTo(1)
    }

    @Test
    fun getsSentiment() {
        val messages: Collection<String?> = Arrays.asList(MESSAGE_1_TEXT, MESSAGE_2_TEXT, MESSAGE_3_TEXT, MESSAGE_4_TEXT, MESSAGE_5_TEXT, MESSAGE_6_TEXT)
        val channelName = RDG.string(20).next()
        val channelMessages = ChannelMessages(channelName, messages, LocalDate.now())
        val results = testee?.getSentimentForSlackMessages(channelMessages)
        Assertions.assertThat(results?.channelName).isEqualTo(channelName)
        Assertions.assertThat(results?.comprehensionDate).isEqualTo(LocalDate.now())
        Assertions.assertThat(results?.results).hasSize(6)
        Assertions.assertThat(results?.sentimentTotals?.get(SentimentType.POSITIVE.toString())).isEqualTo(1)
        Assertions.assertThat(results?.sentimentTotals?.get(SentimentType.MIXED.toString())).isEqualTo(2)
        Assertions.assertThat(results?.sentimentTotals?.get(SentimentType.NEUTRAL.toString())).isEqualTo(3)
        Assertions.assertThat(results?.sentimentTotals?.get(SentimentType.NEGATIVE.toString())).isNull()
        Assertions.assertThat(results?.sentimentScoreTotals?.get(SentimentType.POSITIVE.toString())).isEqualTo(2.1f, Offset.offset(0.001f))
        Assertions.assertThat(results?.sentimentScoreTotals?.get(SentimentType.MIXED.toString())).isEqualTo(2.0f, Offset.offset(0.001f))
        Assertions.assertThat(results?.sentimentScoreTotals?.get(SentimentType.NEUTRAL.toString())).isEqualTo(1.9f, Offset.offset(0.001f))
        Assertions.assertThat(results?.sentimentScoreTotals?.get(SentimentType.NEGATIVE.toString())).isEqualTo(1.8f, Offset.offset(0.001f))
    }

    companion object {
        private const val MESSAGE_1_TEXT = "message1"
        private const val MESSAGE_2_TEXT = "message2"
        private const val MESSAGE_3_TEXT = "message3"
        private const val MESSAGE_4_TEXT = "message4"
        private const val MESSAGE_5_TEXT = "message5"
        private const val MESSAGE_6_TEXT = "message6"
        private const val COMMERCIAL_ITEM_ENTITY = "Entity1"
        private const val EVENT_ENTITY_1 = "Entity2"
        private const val ORGANIZATION_ENTITY = "Entity3"
        private const val EVENT_ENTITY_2 = "Entity4"
        private const val KEY_PHRASE_TEXT_1 = "KeyPhraseText1"
        private const val KEY_PHRASE_TEXT_2 = "KeyPhraseText2"
        private const val KEY_PHRASE_TEXT_3 = "KeyPhraseText3"
    }
}