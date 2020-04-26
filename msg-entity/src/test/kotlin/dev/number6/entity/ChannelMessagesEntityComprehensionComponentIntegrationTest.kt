package dev.number6.entity

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import dev.number6.comprehend.results.PresentableEntityResults
import dev.number6.entity.dagger.DaggerTestChannelMessagesEntityComprehensionComponent
import dev.number6.entity.dagger.FakeComprehensionResultsModule
import dev.number6.message.ChannelMessages
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class ChannelMessagesEntityComprehensionComponentIntegrationTest {
    var testee = DaggerTestChannelMessagesEntityComprehensionComponent.create()

    @Test
    fun providesChannelHandler() {
        val results = mockk<PresentableEntityResults>()
        val handler = testee.getChannelMessagesHandler()
        val f = testee.getResultsFunction() as FakeComprehensionResultsModule.ConfigurableResultsFunction
        f.setPresentableEntityResults(results)
        handler.handle(mockk<ChannelMessages>())
        val consumer = testee.getConsumer() as FakeComprehensionResultsModule.RecordingComprehensionResultsConsumer
        assertThat(consumer.getResultsConsumed()).hasSize(1)
        assertThat(consumer.getResultsConsumed()[0]).isEqualTo(results)
    }
}