package dev.number6.db

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.extracting
import assertk.assertions.hasSize
import dev.number6.db.TestDynamoBasicDatabaseAdaptorComponent.FakeDynamoDBMapper
import dev.number6.db.model.ChannelComprehensionSummary
import org.junit.jupiter.api.Test
import uk.org.fyodor.generators.RDG
import java.time.LocalDate

class DynamoBasicDatabaseAdaptorIntegrationTest {
    private val component = DaggerTestDynamoBasicDatabaseAdaptorComponent.create()

    @Test
    fun createChannels() {
        val channelNames: Collection<String> = RDG.list(RDG.string()).next()
        val mapper = component.fakeDynamoDBMapper as FakeDynamoDBMapper
        component.database.createNewSummaryForChannels(channelNames, LocalDate.now())
        assertThat(mapper.saved).hasSize(channelNames.size)
        assertThat(mapper.saved)
                .extracting(ChannelComprehensionSummary::channelName)
                .containsOnly(*channelNames.toTypedArray())
    }
}