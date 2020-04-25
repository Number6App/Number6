package dev.number6.db

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride
import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.comprehend.results.PresentableSentimentResults
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentScore
import dev.number6.comprehend.results.SentimentResultsToMessageSentimentTotals
import dev.number6.db.adaptor.DatabaseServiceConfigurationAdaptor
import dev.number6.db.adaptor.DynamoDatabaseAdaptor
import dev.number6.db.model.ChannelComprehensionSummary
import dev.number6.db.port.DatabaseConfigurationPort
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import uk.org.fyodor.generators.RDG
import uk.org.fyodor.range.Range
import java.time.LocalDate
import java.util.*
import java.util.function.Consumer

@Disabled("remove Mockito")
internal class DynamoDatabaseAdaptorTest {
    private val mapper = Mockito.mock(DynamoDBMapper::class.java)
    private val dynamoDBMapperConfig = Mockito.mock(DynamoDBMapperConfig::class.java)
    private val dbConfig: DatabaseConfigurationPort = Mockito.mock(DatabaseServiceConfigurationAdaptor::class.java)
    private var testee: DynamoDatabaseAdaptor? = null

    @BeforeEach
    fun setup() {
        val tableNameOverride = TableNameOverride(OVERRIDE_TABLE_NAME)
        Mockito.`when`(dynamoDBMapperConfig.tableNameOverride).thenReturn(tableNameOverride)
        Mockito.`when`(dbConfig.dynamoDBMapperConfig).thenReturn(dynamoDBMapperConfig)
        testee = DynamoDatabaseAdaptor(mapper, dbConfig)
    }

    @Test
    fun savesComprehensionSummary() {
        val channelNames = RDG.list(RDG.string(20), 1).next()
        val comprehensionDate = LocalDate.now()
        val configCaptor = ArgumentCaptor.forClass(DynamoDBMapperConfig::class.java)
        val summaryCaptor = ArgumentCaptor.forClass(ChannelComprehensionSummary::class.java)
        testee!!.createNewSummaryForChannels(channelNames, comprehensionDate)
        Mockito.verify(mapper).save(summaryCaptor.capture(), configCaptor.capture())
        Assertions.assertThat(summaryCaptor.value.channelName).isEqualTo(channelNames[0])
        Assertions.assertThat(summaryCaptor.value.comprehensionDate).isEqualTo(comprehensionDate)
        Assertions.assertThat(configCaptor.value.tableNameOverride.tableName).isEqualTo(OVERRIDE_TABLE_NAME)
    }

    @Test
    fun savesComprehensionSummaryForEachChannel() {
        val channelNames: Collection<String> = RDG.list(RDG.string(), Range.closed(3, 20)).next()
        val comprehensionDate = LocalDate.now()
        val configCaptor = ArgumentCaptor.forClass(DynamoDBMapperConfig::class.java)
        val summaryCaptor = ArgumentCaptor.forClass(ChannelComprehensionSummary::class.java)
        testee!!.createNewSummaryForChannels(channelNames, comprehensionDate)
        Mockito.verify(mapper, Mockito.times(channelNames.size)).save(summaryCaptor.capture(), configCaptor.capture())
//        Assertions.assertThat(summaryCaptor.allValues.stream().map(ChannelComprehensionSummary::channelName).collect(Collectors.toList())).containsExactlyInAnyOrderElementsOf(channelNames)
        summaryCaptor.allValues.forEach(Consumer { c: ChannelComprehensionSummary -> Assertions.assertThat(c.comprehensionDate).isEqualTo(comprehensionDate) })
        configCaptor.allValues.forEach(Consumer { c: DynamoDBMapperConfig -> Assertions.assertThat(c.tableNameOverride.tableName).isEqualTo(OVERRIDE_TABLE_NAME) })
    }

    @Test
    fun saveEntityResults() {
        val channelName = RDG.string(20).next()
        val summary = ChannelComprehensionSummary(channelName, LocalDate.now())
        Mockito.`when`(mapper.load(ArgumentMatchers.any<Class<Any>>(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(summary)
        val detectEntitiesResults = RDG.map(RDG.string(), RDG.map(RDG.string(20), RDG.longVal(Range.closed(1L, 10L)))).next()
        val results = PresentableEntityResults(LocalDate.now(), detectEntitiesResults, channelName)
        val configCaptor = ArgumentCaptor.forClass(DynamoDBMapperConfig::class.java)
        val summaryCaptor = ArgumentCaptor.forClass(ChannelComprehensionSummary::class.java)
        testee!!.save(results)
        Mockito.verify(mapper).save(summaryCaptor.capture(), configCaptor.capture())
        Assertions.assertThat(configCaptor.value.tableNameOverride.tableName).isEqualTo(OVERRIDE_TABLE_NAME)
        Assertions.assertThat(summaryCaptor.value.channelName).isEqualTo(channelName)
        Assertions.assertThat(summaryCaptor.value.comprehensionDate).isEqualTo(LocalDate.now())
        Assertions.assertThat<String, Map<String, Long>>(summaryCaptor.value.entityTotals).isEqualTo(detectEntitiesResults)
    }

    @Test
    fun saveSentimentResults() {
        val channelName = RDG.string(20).next()
        val sentimentScores: MutableMap<String, Float> = HashMap()
        sentimentScores["Sentiment"] = 0.3f
        val sentimentTotals: MutableMap<String, Int> = HashMap()
        sentimentTotals["Entity"] = 2
        val summary = ChannelComprehensionSummary(channelName, LocalDate.now())
        Mockito.`when`(mapper.load(ArgumentMatchers.any<Class<Any>>(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(summary)
        val configCaptor = ArgumentCaptor.forClass(DynamoDBMapperConfig::class.java)
        val summaryCaptor = ArgumentCaptor.forClass(ChannelComprehensionSummary::class.java)
        val sentimentResultsToMessageSentimentScore = Mockito.mock(SentimentResultsToMessageSentimentScore::class.java)
        val sentimentResultsToMessageSentimentTotals = Mockito.mock(SentimentResultsToMessageSentimentTotals::class.java)
        Mockito.`when`(sentimentResultsToMessageSentimentScore.apply(ArgumentMatchers.any())).thenReturn(sentimentScores)
        Mockito.`when`(sentimentResultsToMessageSentimentTotals.apply(ArgumentMatchers.any())).thenReturn(sentimentTotals)
        val sentimentResults = PresentableSentimentResults(LocalDate.now(),
                ArrayList(),
                channelName,
                sentimentResultsToMessageSentimentScore,
                sentimentResultsToMessageSentimentTotals)
        testee!!.save(sentimentResults)
        Mockito.verify(mapper).save(summaryCaptor.capture(), configCaptor.capture())
        Assertions.assertThat(configCaptor.value.tableNameOverride.tableName).isEqualTo(OVERRIDE_TABLE_NAME)
        Assertions.assertThat(summaryCaptor.value.channelName).isEqualTo(channelName)
        Assertions.assertThat(summaryCaptor.value.comprehensionDate).isEqualTo(LocalDate.now())
        Assertions.assertThat(summaryCaptor.value.sentimentScoreTotals).isEqualTo(sentimentScores)
        Assertions.assertThat(summaryCaptor.value.sentimentTotals).isEqualTo(sentimentTotals)
    }

    companion object {
        private const val OVERRIDE_TABLE_NAME = "OverrideTableName"
    }
}