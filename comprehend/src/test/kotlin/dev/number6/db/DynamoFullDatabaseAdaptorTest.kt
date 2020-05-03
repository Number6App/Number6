package dev.number6.db

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride
import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentScore
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentTotals
import dev.number6.db.adaptor.DatabaseServiceConfigurationAdaptor
import dev.number6.db.adaptor.DynamoFullDatabaseAdaptor
import dev.number6.db.model.ChannelComprehensionSummary
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range
import java.time.LocalDate
import java.util.*


@ExtendWith(MockKExtension::class)
internal class DynamoFullDatabaseAdaptorTest {
    private val mapper = mockk<DynamoDBMapper>(relaxUnitFun = true)
    private val dynamoDBMapperConfig = mockk<DynamoDBMapperConfig>()
    private val dbConfig = mockk<DatabaseServiceConfigurationAdaptor>()
    private val testee = DynamoFullDatabaseAdaptor(mapper, dbConfig)

    @BeforeEach
    fun setup() {
        val tableNameOverride = TableNameOverride(OVERRIDE_TABLE_NAME)
        every { dynamoDBMapperConfig.tableNameOverride } returns tableNameOverride
        every { dbConfig.dynamoDBMapperConfig } returns dynamoDBMapperConfig
    }

    @Test
    fun savesComprehensionSummary() {
        val channelNames = RDG.list(RDG.string(20), 1).next()
        val comprehensionDate = LocalDate.now()
        val configSlot = slot<DynamoDBMapperConfig>()
        val summarySlot = slot<ChannelComprehensionSummary>()
        testee.createNewSummaryForChannels(channelNames, comprehensionDate)
        verify(exactly = 1) { mapper.save(capture(summarySlot), capture(configSlot)) }
        assertThat(summarySlot.captured.channelName).isEqualTo(channelNames[0])
        assertThat(summarySlot.captured.comprehensionDate).isEqualTo(comprehensionDate)
        assertThat(configSlot.captured.tableNameOverride.tableName).isEqualTo(OVERRIDE_TABLE_NAME)
    }

    @Test
    fun savesComprehensionSummaryForEachChannel() {
        val channelNames = RDG.list(RDG.string(), Range.closed(3, 20)).next()
        val comprehensionDate = LocalDate.now()
        val configSlot = mutableListOf<DynamoDBMapperConfig>()
        val summarySlot = mutableListOf<ChannelComprehensionSummary>()
        testee.createNewSummaryForChannels(channelNames, comprehensionDate)
        verify(exactly = channelNames.size) { mapper.save(capture(summarySlot), capture(configSlot)) }
//        Assertions.assertThat(summaryCaptor.allValues.stream().map(ChannelComprehensionSummary::channelName).collect(Collectors.toList())).containsExactlyInAnyOrderElementsOf(channelNames)
        summarySlot.forEach { c -> assertThat(c.comprehensionDate).isEqualTo(comprehensionDate) }
        configSlot.forEach { c -> assertThat(c.tableNameOverride.tableName).isEqualTo(OVERRIDE_TABLE_NAME) }
    }

    @Test
    fun saveEntityResults() {
        val channelName = RDG.string(20).next()
        val summary = ChannelComprehensionSummary(channelName, LocalDate.now())
        every { mapper.load(any<Class<Any>>(), any(), any(), any()) } returns summary
        val detectEntitiesResults = RDG.map(RDG.string(), RDG.map(RDG.string(20), RDG.longVal(Range.closed(1L, 10L)))).next()
        val results = PresentableEntityResults(LocalDate.now(), detectEntitiesResults, channelName)
        val configCaptor = slot<DynamoDBMapperConfig>()
        val summaryCaptor = slot<ChannelComprehensionSummary>()
        testee.save(results)
        verify { mapper.save(capture(summaryCaptor), capture(configCaptor)) }
        assertThat(configCaptor.captured.tableNameOverride.tableName).isEqualTo(OVERRIDE_TABLE_NAME)
        assertThat(summaryCaptor.captured.channelName).isEqualTo(channelName)
        assertThat(summaryCaptor.captured.comprehensionDate).isEqualTo(LocalDate.now())
        assertThat(summaryCaptor.captured.entityTotals).isEqualTo(detectEntitiesResults)
    }

    @Test
    fun saveSentimentResults() {
        val channelName = RDG.string(20).next()
        val sentimentScores: MutableMap<String, Float> = HashMap()
        sentimentScores["Sentiment"] = 0.3f
        val sentimentTotals: MutableMap<String, Int> = HashMap()
        sentimentTotals["Entity"] = 2
        val summary = ChannelComprehensionSummary(channelName, LocalDate.now())
        every { mapper.load(any<Class<Any>>(), any(), any(), any()) } returns summary
        val configCaptor = slot<DynamoDBMapperConfig>()
        val summaryCaptor = slot<ChannelComprehensionSummary>()
        val sentimentResultsToMessageSentimentScore = mockk<SentimentResultsToMessageSentimentScore>()
        val sentimentResultsToMessageSentimentTotals = mockk<SentimentResultsToMessageSentimentTotals>()
        every { sentimentResultsToMessageSentimentScore.apply(any()) } returns sentimentScores
        every { sentimentResultsToMessageSentimentTotals.apply(any()) } returns sentimentTotals
        val sentimentResults = PresentableSentimentResults(LocalDate.now(),
                ArrayList(),
                channelName,
                sentimentResultsToMessageSentimentScore,
                sentimentResultsToMessageSentimentTotals)
        testee.save(sentimentResults)
        verify { mapper.save(capture(summaryCaptor), capture(configCaptor)) }
        assertThat(configCaptor.captured.tableNameOverride.tableName).isEqualTo(OVERRIDE_TABLE_NAME)
        assertThat(summaryCaptor.captured.channelName).isEqualTo(channelName)
        assertThat(summaryCaptor.captured.comprehensionDate).isEqualTo(LocalDate.now())
        assertThat(summaryCaptor.captured.sentimentScoreTotals).isEqualTo(sentimentScores)
        assertThat(summaryCaptor.captured.sentimentTotals).isEqualTo(sentimentTotals)
    }

    companion object {
        private const val OVERRIDE_TABLE_NAME = "OverrideTableName"
    }
}