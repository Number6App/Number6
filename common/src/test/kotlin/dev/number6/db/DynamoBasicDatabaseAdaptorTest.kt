package dev.number6.db

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isIn
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride
import dev.number6.db.adaptor.DatabaseServiceConfigurationAdaptor
import dev.number6.db.adaptor.DynamoBasicDatabaseAdaptor
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


@ExtendWith(MockKExtension::class)
internal class DynamoBasicDatabaseAdaptorTest {
    private val mapper = mockk<DynamoDBMapper>(relaxUnitFun = true)
    private val dynamoDBMapperConfig = mockk<DynamoDBMapperConfig>()
    private val dbConfig = mockk<DatabaseServiceConfigurationAdaptor>()
    private val testee = DynamoBasicDatabaseAdaptor(mapper, dbConfig)

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
        val channelNames = RDG.list(RDG.string(), Range.closed(3, 20)).next().toMutableList()
        val comprehensionDate = LocalDate.now()
        val configSlot = mutableListOf<DynamoDBMapperConfig>()
        val summarySlot = mutableListOf<ChannelComprehensionSummary>()
        testee.createNewSummaryForChannels(channelNames, comprehensionDate)
        verify(exactly = channelNames.size) { mapper.save(capture(summarySlot), capture(configSlot)) }
        summarySlot.forEach { c ->
            assertThat(c.comprehensionDate).isEqualTo(comprehensionDate)
            assertThat(c.channelName).isIn(*channelNames.toTypedArray())
            channelNames.remove(c.channelName)
        }
        assertThat(channelNames).isEmpty()
        configSlot.forEach { c -> assertThat(c.tableNameOverride.tableName).isEqualTo(OVERRIDE_TABLE_NAME) }
    }

    companion object {
        private const val OVERRIDE_TABLE_NAME = "OverrideTableName"
    }
}