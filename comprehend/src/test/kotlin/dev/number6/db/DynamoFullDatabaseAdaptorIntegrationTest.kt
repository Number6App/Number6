package dev.number6.db

import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.extracting
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import dev.number6.db.dagger.DaggerTestDynamoFullDatabaseAdaptorComponent
import dev.number6.db.dagger.TestDynamoFullDatabaseAdaptorComponent
import dev.number6.db.model.ChannelComprehensionSummary
import dev.number6.db.port.FullDatabasePort
import dev.number6.generate.ComprehendRDG
import org.junit.jupiter.api.Test
import uk.org.fyodor.generators.RDG
import java.time.LocalDate
import java.util.function.Consumer

class DynamoFullDatabaseAdaptorIntegrationTest {
    private val component = DaggerTestDynamoFullDatabaseAdaptorComponent.create()

    @Test
    fun createChannels() {
        val channelNames: Collection<String> = RDG.list(RDG.string()).next()
        val mapper = component.fakeDynamoDBMapper as TestDynamoFullDatabaseAdaptorComponent.FakeDynamoDBMapper
        component.database.createNewSummaryForChannels(channelNames, LocalDate.now())
        assertThat(mapper.saved).hasSize(channelNames.size)
        assertThat(mapper.saved)
                .extracting(ChannelComprehensionSummary::channelName)
                .containsOnly(*channelNames.toTypedArray())
    }

    @Test
    fun saveEntityResults() {
        val results = ComprehendRDG.presentableEntityResults().next()
        val summary = saveResults(Consumer { m -> m.save(results) })
        assertThat(summary.entityTotals).isEqualTo(results.results)
    }

    @Test
    fun saveSentimentResults() {
        val results = ComprehendRDG.presentableSentimentResults().next()
        val summary = saveResults(Consumer { m -> m.save(results) })
        assertThat(summary.sentimentTotals).isEqualTo(results.sentimentTotals)
        assertThat(summary.sentimentScoreTotals).isEqualTo(results.sentimentScoreTotals)
    }

    @Test
    fun saveKeyPhrasesResults() {
        val results = ComprehendRDG.presentableKeyPhrasesResults().next()
        val summary = saveResults(Consumer { m -> m.save(results) })
        assertThat(summary.keyPhrasesTotals).isEqualTo(results.results)
    }

    private fun saveResults(resultsConsumer: Consumer<FullDatabasePort>): ChannelComprehensionSummary {
        val mapper = component.fakeDynamoDBMapper as TestDynamoFullDatabaseAdaptorComponent.FakeDynamoDBMapper
        resultsConsumer.accept(component.database)
        assertThat(mapper.saved).hasSize(1)
        return mapper.saved[0]
    }
}