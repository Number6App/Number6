package dev.number6.comprehend

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThanOrEqualTo
import assertk.assertions.isNull
import com.amazonaws.services.comprehend.model.*
import dev.number6.comprehend.adaptor.AwsComprehensionAdaptor
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentScore
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentTotals
import dev.number6.message.ChannelMessages
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.org.fyodor.generators.RDG
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
internal class AwsComprehensionAdaptorTest {
    private val client = mockk<AwsComprehendClient>()
    private val sentimentResultsToMessageSentimentScore = mockk<SentimentResultsToMessageSentimentScore>()
    private val sentimentResultsToMessageSentimentTotals = mockk<SentimentResultsToMessageSentimentTotals>()
    private var messageEntityResults: Map<String, DetectEntitiesResult>? = null
    private var messageKeyPhrasesResults: Map<String, DetectKeyPhrasesResult>? = null
    private var messageSentimentResults: Map<String, DetectSentimentResult>? = null
    private val testee: AwsComprehensionAdaptor = AwsComprehensionAdaptor(client)

    @BeforeEach
    fun setup() {
        every { client.getMessageEntities(any()) } answers {
            messageEntityResults?.get(firstArg<Any>()) ?: DetectEntitiesResult()
        }
        every { client.getMessageKeyPhrases(any()) } answers {
            messageKeyPhrasesResults?.get(firstArg<Any>()) ?: DetectKeyPhrasesResult()
        }
        every { client.getMessageSentiment(any()) } answers {
            messageSentimentResults?.get(firstArg<Any>()) ?: DetectSentimentResult()
        }
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
        every { sentimentResultsToMessageSentimentScore.apply(any()) } returns sentimentScores
        every { sentimentResultsToMessageSentimentTotals.apply(any()) } returns sentimentTotals
    }

    @BeforeEach
    fun setMessageKeyPhrasesResults() {
        messageKeyPhrasesResults = mapOf(MESSAGE_1_TEXT to DetectKeyPhrasesResult().withKeyPhrases(KeyPhrase().withText(KEY_PHRASE_TEXT_1)),
                MESSAGE_2_TEXT to DetectKeyPhrasesResult().withKeyPhrases(KeyPhrase().withText(KEY_PHRASE_TEXT_2),
                        KeyPhrase().withText(KEY_PHRASE_TEXT_1)),
                MESSAGE_3_TEXT to DetectKeyPhrasesResult().withKeyPhrases(KeyPhrase().withText(KEY_PHRASE_TEXT_3),
                        KeyPhrase().withText(KEY_PHRASE_TEXT_2),
                        KeyPhrase().withText(KEY_PHRASE_TEXT_1))
        )
    }

    @BeforeEach
    fun setMessageEntityResults() {
        messageEntityResults = mapOf(MESSAGE_1_TEXT to DetectEntitiesResult().withEntities(Entity().withText(COMMERCIAL_ITEM_ENTITY).withType(EntityType.COMMERCIAL_ITEM)),
                MESSAGE_2_TEXT to DetectEntitiesResult().withEntities(Entity().withText(COMMERCIAL_ITEM_ENTITY).withType(EntityType.COMMERCIAL_ITEM),
                        Entity().withText(EVENT_ENTITY_1).withType(EntityType.EVENT)),
                MESSAGE_3_TEXT to DetectEntitiesResult().withEntities(Entity().withText(COMMERCIAL_ITEM_ENTITY).withType(EntityType.COMMERCIAL_ITEM),
                        Entity().withText(EVENT_ENTITY_1).withType(EntityType.EVENT),
                        Entity().withText(EVENT_ENTITY_2).withType(EntityType.EVENT),
                        Entity().withText(ORGANIZATION_ENTITY).withType(EntityType.ORGANIZATION)
                ))
    }

    @BeforeEach
    fun setMessageSentimentResults() {
        messageSentimentResults = mapOf(MESSAGE_1_TEXT to DetectSentimentResult()
                .withSentiment(SentimentType.NEUTRAL),
                MESSAGE_2_TEXT to DetectSentimentResult()
                        .withSentiment(SentimentType.NEUTRAL)
                        .withSentimentScore(SentimentScore().withMixed(0.2f).withNegative(0.1f).withNeutral(0.2f).withPositive(0.2f)),
                MESSAGE_3_TEXT to DetectSentimentResult()
                        .withSentiment(SentimentType.NEUTRAL)
                        .withSentimentScore(SentimentScore().withMixed(0.3f).withNegative(0.2f).withNeutral(0.3f).withPositive(0.3f)),
                MESSAGE_4_TEXT to DetectSentimentResult()
                        .withSentiment(SentimentType.MIXED)
                        .withSentimentScore(SentimentScore().withMixed(0.4f).withNegative(0.3f).withNeutral(0.3f).withPositive(0.4f)),
                MESSAGE_5_TEXT to DetectSentimentResult()
                        .withSentiment(SentimentType.MIXED)
                        .withSentimentScore(SentimentScore().withMixed(0.5f).withNegative(0.5f).withNeutral(0.4f).withPositive(0.5f)),
                MESSAGE_6_TEXT to DetectSentimentResult()
                        .withSentiment(SentimentType.POSITIVE)
                        .withSentimentScore(SentimentScore().withMixed(0.5f).withNegative(0.6f).withNeutral(0.6f).withPositive(0.6f))
        )
    }

    @Test
    fun getsEntities() {
        val messages = listOf(MESSAGE_1_TEXT, MESSAGE_2_TEXT, MESSAGE_3_TEXT)
        val channelName = RDG.string(20).next()
        val channelMessages = ChannelMessages(channelName, messages, LocalDate.now())
        val results = testee.getEntitiesForSlackMessages(channelMessages)
        assertThat(results.channelName).isEqualTo(channelName)
        assertThat(results.comprehensionDate).isEqualTo(LocalDate.now())
        assertThat(results.results).hasSize(3)
        assertThat(results.presentableResults[EntityType.COMMERCIAL_ITEM.toString()] ?: mapOf()).hasSize(1)
        assertThat(results.presentableResults[EntityType.COMMERCIAL_ITEM.toString()]?.get(COMMERCIAL_ITEM_ENTITY)).isEqualTo(3)
        assertThat(results.presentableResults[EntityType.EVENT.toString()] ?: mapOf()).hasSize(2)
        assertThat(results.presentableResults[EntityType.EVENT.toString()]?.get(EVENT_ENTITY_1)).isEqualTo(2)
        assertThat(results.presentableResults[EntityType.EVENT.toString()]?.get(EVENT_ENTITY_2)).isEqualTo(1)
        assertThat(results.presentableResults[EntityType.ORGANIZATION.toString()] ?: mapOf()).hasSize(1)
        assertThat(results.presentableResults[EntityType.ORGANIZATION.toString()]?.get(ORGANIZATION_ENTITY)).isEqualTo(1)
    }

    @Test
    fun getsKeyPhrases() {
        val messages = listOf(MESSAGE_1_TEXT, MESSAGE_2_TEXT, MESSAGE_3_TEXT)
        val channelName = RDG.string(20).next()
        val channelMessages = ChannelMessages(channelName, messages, LocalDate.now())
        val results = testee.getKeyPhrasesForSlackMessages(channelMessages)
        assertThat(results.channelName).isEqualTo(channelName)
        assertThat(results.comprehensionDate).isEqualTo(LocalDate.now())
        assertThat(results.results).hasSize(3)
        assertThat(results.presentableResults[KEY_PHRASE_TEXT_1]).isEqualTo(3)
        assertThat(results.presentableResults[KEY_PHRASE_TEXT_2]).isEqualTo(2)
        assertThat(results.presentableResults[KEY_PHRASE_TEXT_3]).isEqualTo(1)
    }

    @Test
    fun getsSentiment() {
        val messages = listOf(MESSAGE_1_TEXT, MESSAGE_2_TEXT, MESSAGE_3_TEXT, MESSAGE_4_TEXT, MESSAGE_5_TEXT, MESSAGE_6_TEXT)
        val channelName = RDG.string(20).next()
        val channelMessages = ChannelMessages(channelName, messages, LocalDate.now())
        val results = testee.getSentimentForSlackMessages(channelMessages)
        assertThat(results.channelName).isEqualTo(channelName)
        assertThat(results.comprehensionDate).isEqualTo(LocalDate.now())
        assertThat(results.results).hasSize(6)
        assertThat(results.sentimentTotals[SentimentType.POSITIVE.toString()]).isEqualTo(1)
        assertThat(results.sentimentTotals[SentimentType.MIXED.toString()]).isEqualTo(2)
        assertThat(results.sentimentTotals[SentimentType.NEUTRAL.toString()]).isEqualTo(3)
        assertThat(results.sentimentTotals[SentimentType.NEGATIVE.toString()]).isNull()
        assertThat(results.sentimentScoreTotals[SentimentType.POSITIVE.toString()]?.rem(2.0f)
                ?: 0.0f).isLessThanOrEqualTo(0.001f)//, Offset.offset(0.001f))
        assertThat(results.sentimentScoreTotals[SentimentType.MIXED.toString()]?.rem(1.9f)
                ?: 0.0f).isLessThanOrEqualTo(0.001f)//, Offset.offset(0.001f))
        assertThat(results.sentimentScoreTotals[SentimentType.NEUTRAL.toString()]?.rem(1.8f)
                ?: 0.0f).isLessThanOrEqualTo(0.001f)//, Offset.offset(0.001f))
        assertThat(results.sentimentScoreTotals[SentimentType.NEGATIVE.toString()]?.rem(1.7f)
                ?: 0.0f).isLessThanOrEqualTo(0.001f)//, Offset.offset(0.001f))
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