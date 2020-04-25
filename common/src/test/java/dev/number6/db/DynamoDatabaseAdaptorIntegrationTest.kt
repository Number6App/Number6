package dev.number6.db

import dev.number6.db.TestDynamoDatabaseAdaptorComponent.FakeDynamoDBMapper
import dev.number6.db.model.ChannelComprehensionSummary
import dev.number6.db.port.DatabasePort
import dev.number6.generate.CommonRDG
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import uk.org.fyodor.generators.RDG
import java.time.LocalDate
import java.util.function.Consumer

class DynamoDatabaseAdaptorIntegrationTest {
    private val component = DaggerTestDynamoDatabaseAdaptorComponent.create()

    @Test
    fun createChannels() {
        val channelNames: Collection<String> = RDG.list(RDG.string()).next()
        val mapper = component.fakeDynamoDBMapper as FakeDynamoDBMapper
        component.database.createNewSummaryForChannels(channelNames, LocalDate.now())
        Assertions.assertThat(mapper.saved).hasSize(channelNames.size)
                .extracting("channelName")
                .containsExactlyInAnyOrder(*channelNames.toTypedArray())
    }

    @Test
    fun saveEntityResults() {
        val results = CommonRDG.presentableEntityResults().next()
        val summary = saveResults(Consumer { m: DatabasePort? -> m!!.save(results!!) })
        Assertions.assertThat(summary!!.entityTotals).isEqualTo(results!!.results)
    }

    @Test
    fun saveSentimentResults() {
        val results = CommonRDG.presentableSentimentResults().next()
        val summary = saveResults(Consumer { m: DatabasePort? -> m!!.save(results!!) })
        Assertions.assertThat(summary!!.sentimentTotals).isEqualTo(results!!.sentimentTotals)
        Assertions.assertThat(summary.sentimentScoreTotals).isEqualTo(results.sentimentScoreTotals)
    }

    @Test
    fun saveKeyPhrasesResults() {
        val results = CommonRDG.presentableKeyPhrasesResults().next()
        val summary = saveResults(Consumer { m: DatabasePort? -> m!!.save(results!!) })
        Assertions.assertThat(summary!!.keyPhrasesTotals).isEqualTo(results!!.results)
    }

    private fun saveResults(resultsConsumer: Consumer<DatabasePort?>): ChannelComprehensionSummary? {
        val mapper = component.fakeDynamoDBMapper as FakeDynamoDBMapper
        resultsConsumer.accept(component.database)
        Assertions.assertThat(mapper.saved).hasSize(1)
        return mapper.saved[0]
    }
}